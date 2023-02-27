package com.sms.project.subcription.service;

import com.itextpdf.text.DocumentException;
import com.sms.project.exceptions.ProductException;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import com.sms.project.plandetails.repo.PlanRepository;
import com.sms.project.product.entity.Product;
import com.sms.project.product.repo.ProductRepo;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.entity.SubscriptionDto;
import com.sms.project.subcription.repository.SubscriptionRepo;
import com.sms.project.user.entity.User;
import com.sms.project.user.repo.UserRepo;
import com.sms.project.utility.Invoice;
import com.sms.project.utility.errorcode.ErrorCodes;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubscriptionImpl implements SubscriptionService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private Invoice invoice;

    @Override
    public SubscriptionDto createSubscription(int planId, SubscriptionDto subscriptionDto) throws MessagingException, DocumentException, FileNotFoundException, ProductException {
        log.info("Subscription process is going on now...");
        User userInfo = getLoginUser();
        Optional<PlanDetail> plan = planRepository.findById(planId);
        Subscription subscription = modelMapper.map(subscriptionDto, Subscription.class);

        if(plan.get().getProduct().getStorage()==0) throw new ProductException("LIMIT IS OVER TRY TO GET ANOTHER PRODUCT");
        subscription.setUser(userInfo);
        subscription.setPlanDetail(plan.get());
        User user = getLoginUser();
        String[] ss = user.getRole().toLowerCase().split("_");
        String role = ss[ss.length - 1];
        if (!plan.get().getPlanTypeFor().equalsIgnoreCase(role))
            throw new IllegalArgumentException(ErrorCodes.PLAN_NOT_FOUND);
        if (plan.get().getPrice() != subscription.getAmount())
            throw new IllegalArgumentException(ErrorCodes.AMOUNT_NOT_VALID);
        subscription.setSubscriptionStatus("Active");
        subscription.setSubscriptionStartDate(LocalDate.now());
        LocalDate today = LocalDate.now();
        subscription.setSubscriptionEndDate(today.plusDays(plan.get().getValidityDays()));
        subscription.setSubscriptionDuration(plan.get().getValidityDays());
        Product product = plan.get().getProduct();
        product.setStorage(plan.get().getProduct().getStorage()-1);
        productRepo.save(product);
        subscriptionRepo.save(subscription);
        SubscriptionDto subDto = modelMapper.map(subscription, SubscriptionDto.class);
        try {
            invoice.generateUsersPDF(subscription);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            subDto.setPlanDto(modelMapper.map(subscription.getPlanDetail(), PlanDto.class));
        }
        return subDto;
    }

    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepo.findByEmail(email).get();
    }

    @Override
    public String deleteSubscription(int id) {
        log.info("To remove subscription");
        Optional<Subscription> subDetails = subscriptionRepo.findById(id);
        if (subDetails.isPresent()) {
            subDetails.get().setDeleted_at(LocalDate.now());
            subscriptionRepo.saveAndFlush(subDetails.get());
            return "Subscription Deleted..";
        }
        return "Subscription has not deleted..";
    }

    @Override
    public List<SubscriptionDto> getAllList() {
        List<Subscription> subscriptionList = subscriptionRepo.findAll();
        return subscriptionList.stream().map(subscription -> {
            PlanDetail planDetail = getValue(subscription);
            subscription.setPlanDetail(planDetail);
            SubscriptionDto subscriptionDto = modelMapper.map(subscription, SubscriptionDto.class);
            subscriptionDto.setPlanDto(modelMapper.map(planDetail, PlanDto.class));
            return subscriptionDto;
        }).collect(Collectors.toList());
    }

    private PlanDetail getValue(Subscription subscription) {
        return subscription.getPlanDetail();
    }

    public List<Subscription> getAllSubscriptionsList(int id) {
        return subscriptionRepo.findList(id);
    }

    @Override
    public ResponseEntity<SubscriptionDto> getSubscriptionById(int subscriptionId) {
        Subscription subscription = subscriptionRepo.findById(subscriptionId).orElseThrow(() -> new IllegalArgumentException(ErrorCodes.SUBSCRIPTION_NOT_VALID));
        SubscriptionDto subDto = modelMapper.map(subscription, SubscriptionDto.class);
        subDto.setPlanDto(modelMapper.map(subscription.getPlanDetail(), PlanDto.class));
        return new ResponseEntity<>(subDto, HttpStatus.OK);
    }

}
