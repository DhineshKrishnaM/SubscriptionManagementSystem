package com.sms.project.plandetails.service;

import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import com.sms.project.plandetails.repo.PlanRepository;
import com.sms.project.product.entity.Product;
import com.sms.project.product.repo.ProductRepo;
import com.sms.project.user.entity.User;
import com.sms.project.user.repo.UserRepo;
import com.sms.project.utility.errorcode.ErrorCodes;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class PlanServiceImpl implements PlanService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    public List<PlanDto> listOfPlans() {
        List<PlanDetail> planList = planRepository.findAll();
        log.info("List of plan executed Successfully");
        List<PlanDto> sortList = planList.stream().map(planDetail -> {
            PlanDto plan = modelMapper.map(planDetail, PlanDto.class);
            return plan;

        }).collect(Collectors.toList());
        return sortList;
    }

    @Override
    public List<PlanDetail> listOfPlanBasedOnUser(String planType) {
        log.info("List of plan based on PlanType executed");
        return planRepository.findAllByPlanType(planType);
    }

    @Override
    public PlanDto postNewPlan(int productId, PlanDto newPlan) {
        log.info("New plan added");
        Optional<Product> productGet = productRepo.findById(productId);
        PlanDetail planDetail = modelMapper.map(newPlan, PlanDetail.class);
        planDetail.setProduct(productGet.get());
        planRepository.save(planDetail);
        PlanDto planDto = modelMapper.map(planDetail, PlanDto.class);
        return planDto;
    }

    @Override
    public String deletePlanByWriter(int planId) {
        log.info(planId + " Plan has been deleted");
        PlanDetail planDetail = planRepository.findById(planId).orElseThrow(() -> new IllegalArgumentException(ErrorCodes.PLAN_NOT_FOUND));
        planDetail.setDeleted_at(LocalDate.now());
        planRepository.saveAndFlush(planDetail);
        return "Plan Deleted";
    }

    @Override
    public PlanDto updatePlan(int planId, PlanDto plan) {
        Optional<PlanDetail> planDet = planRepository.findById(planId);
        if(planDet.isPresent()){
        planDet.get().setPlanName(plan.getPlanName());
        planDet.get().setPlanTypeFor(plan.getPlanTypeFor());}
        PlanDetail planValue = planRepository.save(planDet.get());
        log.info("The Plan Details updated for id: " + planId);
        PlanDto planDto = modelMapper.map(planValue, PlanDto.class);
        return planDto;
    }

    @Override
    public PlanDto getById(int planId) {
        Optional<PlanDetail> plan = planRepository.findById(planId);
        if(plan.isPresent()){
            return modelMapper.map(plan.get(),PlanDto.class);
        }
        return null;
    }
}
