package com.sms.project.ServiceTest;

import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import com.sms.project.plandetails.repo.PlanRepository;
import com.sms.project.plandetails.service.PlanServiceImpl;
import com.sms.project.product.entity.Product;
import com.sms.project.product.repo.ProductRepo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {
    @Mock
    ModelMapper modelMapper;

    PlanDetail plan = new PlanDetail();
    PlanDto planDto = new PlanDto();
    PlanDto planDto1 = new PlanDto();
    Optional<PlanDetail> plan1 = Optional.of(new PlanDetail());
    Optional<Product> product = Optional.of(new Product());
    List<PlanDetail> planList = Arrays.asList(plan,plan);
    List<PlanDto> planDtoList;
    @Mock
    PlanRepository planRepository;
    @Mock
    ProductRepo productRepo;
    @InjectMocks
    PlanServiceImpl planService;
    @Mock
    CommonEntity commonEntity;

    @BeforeEach
    void setup() {
        plan.setId(2);
        plan.setDeleted_at(null);
        plan.setPrice(1000);
        plan.setValidityDays(7);
        plan.setPlanTypeFor("b2b");
        plan.setPlanName("month");


        planDto = PlanDto.builder()
                .planName("5GB/month")
                .planTypeFor("B2C")
                .validityDays(30)
                .price(1500)
                .build();
        planDto1 = PlanDto.builder()
                .planName("5GB/month")
                .planTypeFor("B2C")
                .validityDays(30)
                .price(1500)
                .build();
    }
    @Before
    void list(){
        planDtoList.add(planDto);
        planDtoList.add(planDto1);
    }
    @DisplayName("To create a new plan")
    @Test
    void saveNewPlan(){
        Mockito.when(productRepo.findById(1)).thenReturn(product);
        Mockito.when(modelMapper.map(planDto,PlanDetail.class)).thenReturn(plan);
        Mockito.when(planRepository.save(plan)).thenReturn(plan);
        Mockito.when(modelMapper.map(plan,PlanDto.class)).thenReturn(planDto);
        Assertions.assertThat(planService.postNewPlan(1,planDto)).isEqualTo(planDto);
    }
    @DisplayName("To get listOf plans")
    @Test
    void listOfPlan(){
        Mockito.when(planRepository.findAll()).thenReturn(planList);
        Mockito.when(modelMapper.map(plan,PlanDto.class)).thenReturn(planDto);
        Assertions.assertThat(planService.listOfPlans()).hasSize(2);
    }
    @DisplayName("Update a existing plan")
    @Test
    void updatePlan(){
        Mockito.when(planRepository.findById(2)).thenReturn(plan1);
        Mockito.when(planRepository.save(plan1.get())).thenReturn(plan1.get());
        Mockito.when(modelMapper.map(plan1.get(),PlanDto.class)).thenReturn(planDto);
        Assertions.assertThat(planService.updatePlan(2,planDto)).isEqualTo(planDto);
    }
    @Test
    void deletePlan(){
        Mockito.when(planRepository.findById(1)).thenReturn(plan1);
        Mockito.when(planRepository.saveAndFlush(plan1.get())).thenReturn(plan);
        Assertions.assertThat(planService.deletePlanByWriter(1)).isEqualTo("Plan Deleted");
    }


}
