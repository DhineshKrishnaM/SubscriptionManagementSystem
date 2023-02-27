package com.sms.project.ControllerTest;

import com.sms.project.plandetails.controller.PlanController;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import com.sms.project.plandetails.repo.PlanRepository;
import com.sms.project.plandetails.service.PlanServiceImpl;
import com.sms.project.product.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PlanControllerTest {
    PlanDetail plan = new PlanDetail();
    PlanDto planDto = new PlanDto();
    Optional<PlanDetail> listPlans = Optional.of(new PlanDetail());
    List<PlanDetail> planList = new ArrayList<>();
    List<PlanDto> listDto=new ArrayList<>();
    Product product = new Product();
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PlanServiceImpl planService;
    @InjectMocks
    private PlanController controller;
    @Mock
    private PlanRepository planRepository;

    @BeforeEach
    void setup() {
        product = Product.builder()
                .productName("Java")
                .description("For Advanced")
                .build();

        plan = PlanDetail.builder()
                .planName("5GB/Week")
                .planTypeFor("b2b")
                .price(1000)
                .validityDays(7)
                .build();
        planDto = PlanDto.builder()
                .planName("5GB/Week")
                .planTypeFor("b2b")
                .price(1000)
                .validityDays(7)
                .build();
    }

    @DisplayName("To post or create a new plan")
    @Test
    void saveNewPlan() {
        Mockito.when(planService.postNewPlan(1, planDto)).thenReturn(planDto);
        Assertions.assertThat(controller.createNewPlan(1, planDto).getStatusCodeValue()).isEqualTo(200);
    }

    @DisplayName("It provides list of plans")
    @Test
    void listOfPlans() {
        Mockito.when(planService.listOfPlans()).thenReturn(listDto);
        Assertions.assertThat(controller.listOfPlan().getStatusCodeValue()).isEqualTo(200);
    }

    @DisplayName("This method for update the plan by using planId")
    @Test
    void planUpdate() {
        Mockito.when(planService.updatePlan(1, planDto)).thenReturn(planDto);
        Assertions.assertThat(controller.planUpdate(1, planDto)).isEqualTo(planDto);
    }

    @DisplayName("Delete plan by ID")
    @Test
    void deletePlan() {
        Mockito.when(planService.deletePlanByWriter(1)).thenReturn("Plan Deleted");
        Assertions.assertThat(controller.deletePlan(1)).isEqualTo("Plan deleted");
    }
}
