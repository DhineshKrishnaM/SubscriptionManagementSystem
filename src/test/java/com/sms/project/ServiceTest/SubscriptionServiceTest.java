//package com.sms.project.ServiceTest;
//
//import com.sms.project.plandetails.entity.PlanDetail;
//import com.sms.project.plandetails.repo.PlanRepository;
//import com.sms.project.product.entity.Product;
//import com.sms.project.subcription.entity.Subscription;
//import com.sms.project.subcription.entity.SubscriptionDto;
//import com.sms.project.subcription.repository.SubscriptionRepo;
//import com.sms.project.subcription.service.SubscriptionImpl;
//import com.sms.project.user.entity.User;
//import com.sms.project.user.repo.UserRepo;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@ExtendWith(MockitoExtension.class)
//public class SubscriptionServiceTest {
//    @Mock
//    ModelMapper modelMapper;
//    @Mock
//    SubscriptionRepo subscriptionRepo;
//    @InjectMocks
//    SubscriptionImpl subscriptionImpl;
//    @Mock
//    PlanRepository planRepository;
//    @Mock
//    Subscription subscription;
//    @Mock
//    UserRepo userRepo;
//    @Mock
//    SubscriptionDto subscriptionDto;
//    List<Subscription> subscriptionList = Arrays.asList(subscription, subscription);
//    List<SubscriptionDto> subscriptionDtos = Arrays.asList(subscriptionDto, subscriptionDto);
//    Product product = new Product();
//
//    User user = new User();
//    @Mock
//    PlanDetail planDetail = new PlanDetail();
//    @Mock
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//    Optional<PlanDetail> planDetailOptional = Optional.of(planDetail);
//
////    Optional<Subscription> subscriptionOptional = Optional.of(subscription);
//
//    @BeforeEach
//    void setup() {
//
//        product.setId(1);
//        product.setDeleted_at(null);
//        product.setProductName("Jython");
//        product.setDescription("From scrstch");
//
//        user.setId(1);
//        user.setDeleted_at(null);
//        user.setEmail("mani@gmail.com");
//        user.setPassword("swd@123");
//        user.setRole("role_b2b");
//
//        planDetail.setId(1);
//        planDetail.setDeleted_at(null);
//        planDetail.setPlanName("ForMonth");
//        planDetail.setPlanTypeFor("b2b");
//        planDetail.setValidityDays(7);
//        planDetail.setPrice(1000);
//        planDetail.setProduct(product);
//
//        subscription.setId(1);
//        subscription.setDeleted_at(null);
//        subscription.setSubscriptionStartDate(LocalDate.now());
//        subscription.setSubscriptionEndDate(LocalDate.now().plusDays(7));
//        subscription.setSubscriptionDuration(7);
//        subscription.setSubscriptionStatus("active");
//        subscription.setPlanDetail(planDetail);
//        subscription.setUser(user);
//
//        subscriptionDto = SubscriptionDto.builder()
//                .subscriptionDuration(7)
//                .subscriptionStartDate(LocalDate.now())
//                .subscriptionEndDate(LocalDate.now().plusDays(7))
//                .subscriptionStatus("active")
//                .build();
//    }
//
//    @Test
//    void saveSubscription() {
//        Mockito.when(authentication.getName()).thenReturn("mani@gmail.com");
//        Mockito.when(planRepository.findById(1)).thenReturn(Optional.ofNullable(planDetail));
//        Mockito.when(modelMapper.map(subscriptionDto, Subscription.class)).thenReturn(subscription);
//        Mockito.when(subscriptionRepo.save(subscription)).thenReturn(subscription);
//        Mockito.when(modelMapper.map(subscription, SubscriptionDto.class)).thenReturn(subscriptionDto);
//        Assertions.assertThat(subscriptionImpl.createSubscription(1, subscriptionDto)).isEqualTo(subscriptionDtos);
//    }
//
//    @Test
//    void deleteSubscription() {
//        Mockito.when(subscriptionRepo.findById(1)).thenReturn(Optional.ofNullable(subscription));
//        Mockito.when(subscriptionRepo.saveAndFlush(subscription)).thenReturn(subscription);
//        Assertions.assertThat(subscriptionImpl.deleteSubscription(1)).isEqualTo("Subscription removed");
//    }
//
//    @Test
//    void getSubscriptionList() {
//        Mockito.when(subscriptionRepo.findAll()).thenReturn(subscriptionList);
//        Assertions.assertThat(subscriptionImpl.getAllList()).isNotEmpty();
//    }
//
//
//}
