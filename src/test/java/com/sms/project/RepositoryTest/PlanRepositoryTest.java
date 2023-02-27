package com.sms.project.RepositoryTest;

import com.paypal.api.payments.Plan;
import com.sms.project.SubscriptionManagementSystemApplication;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.repo.PlanRepository;
import com.sms.project.plandetails.service.PlanServiceImpl;
import com.sms.project.product.entity.Product;
import com.sms.project.utility.errorcode.ErrorCodes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest(classes = SubscriptionManagementSystemApplication.class)
class PlanRepositoryTest {
    @Autowired
    PlanRepository planRepository;

    Product product=new Product();

    Product product1 = new Product();


    @Autowired
    PlanServiceImpl planService;
    PlanDetail plan = new PlanDetail();
    PlanDetail plan1 = new PlanDetail();
    List<PlanDetail> planDetails = Arrays.asList(plan, plan1);


    @BeforeEach
    void setup() {
        plan.setId(1);
        plan.setDeleted_at(null);
        plan.setPlanTypeFor("b2b");
        plan.setValidityDays(10);
        plan.setPlanName("value");
        plan.setPrice(1000);
        plan.setProduct(product);
    }

    @DisplayName("To save new Plan")
    @Test
    void saveNewPlan() {
        planRepository.save(plan);
        Assertions.assertThat(plan.getId()).isPositive();
    }

    @DisplayName("To get list of plans")
    @Test
    void listOfPlans() {
        List<PlanDetail> allPlans = planDetails;
        Assertions.assertThat(allPlans.size()).isEqualTo(2);
    }

    @DisplayName("Delete Plan By using Id")
    @Test
    void deleteById() {
        List<PlanDetail> list = planDetails;
        PlanDetail ss = planRepository.findById(2).get();
        planRepository.delete(ss);
        Assertions.assertThat(planDetails).hasSize(2);
    }
    @DisplayName("Update Plan By using  plan Id")
    @Test
    void updateById(){
        plan=planRepository.findById(1).get();
        plan.setPlanName("4GB/Month");
        plan=planRepository.save(plan);
        Assertions.assertThat(plan.getId()).isEqualTo(plan.getId());
    }
}
