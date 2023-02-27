package com.sms.project.RepositoryTest;

import com.sms.project.SubscriptionManagementSystemApplication;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.product.entity.Product;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.entity.SubscriptionDto;
import com.sms.project.subcription.repository.SubscriptionRepo;
import com.sms.project.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = SubscriptionManagementSystemApplication.class)
public class SubscriptionRepoTest {
    Subscription subscription1 = new Subscription();
    @Autowired
    private SubscriptionRepo subscriptionRepo;
    @Mock
    private Subscription subscription;
    List<Subscription> list = Arrays.asList(subscription, subscription1);
    @Mock
    private PlanDetail planDetail;
    @Mock
    private User user;
    @Mock
    private Product product;
    @Mock
    private SubscriptionDto subscriptionDto;

    @BeforeEach
    void setup() {
        product.setId(1);
        product.setDeleted_at(null);
        product.setProductName("Jython");
        product.setDescription("From scrstch");

        user.setId(1);
        user.setDeleted_at(null);
        user.setEmail("mani@gmail.com");
        user.setPassword("swd@123");
        user.setRole("role_b2b");

        planDetail.setId(1);
        planDetail.setDeleted_at(null);
        planDetail.setPlanName("ForMonth");
        planDetail.setPlanTypeFor("b2b");
        planDetail.setValidityDays(7);
        planDetail.setPrice(1000);
        planDetail.setProduct(product);

        subscription.setId(1);
        subscription.setDeleted_at(null);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusDays(7));
        subscription.setSubscriptionDuration(7);
        subscription.setSubscriptionStatus("active");
        subscription.setPlanDetail(planDetail);
        subscription.setUser(user);

        subscriptionDto = SubscriptionDto.builder()
                .subscriptionStartDate(LocalDate.now())
                .subscriptionEndDate(LocalDate.now().plusDays(5))
                .subscriptionStatus("active")
                .subscriptionDuration(7)
                .build();
    }

    @DisplayName("Create new Subscription")
    @Test
    void saveSubscription() {
        Subscription subscription1 = Subscription.builder()
                .subscriptionStatus("Basic Python")
                .subscriptionDuration(7)
                .subscriptionStartDate(LocalDate.now())
                .subscriptionEndDate(LocalDate.now().plusDays(7))
                .build();
        subscriptionRepo.save(subscription1);
        Assertions.assertThat(subscription1.getId()).isPositive();
    }

    @DisplayName("To get list of subscription")
    @Test
    void getList() {
        List<Subscription> sub = subscriptionRepo.findAll();
        Assertions.assertThat(sub).isNotEmpty();
    }

    @DisplayName("Test case for remove subscription")
    @Test
    void deleteSubscription() {
        List<Subscription> listOfSub = subscriptionRepo.findAll();
        subscription = subscriptionRepo.findById(1).get();
        List<Subscription> temp = subscriptionRepo.findAll();
        subscriptionRepo.delete(subscription);
        Assertions.assertThat(listOfSub.size()).isEqualTo(temp.size());
    }
    @DisplayName("Update subscription")
    @Test
    void updateSubscription() {
        Subscription subscription = subscriptionRepo.findById(1).get();
        subscription.setSubscriptionStatus("active");
        Subscription subscription2 = subscriptionRepo.save(subscription);
        Assertions.assertThat(subscription2.getId()).isEqualTo(subscription2.getId());
    }
}
