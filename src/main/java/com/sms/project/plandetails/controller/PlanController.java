package com.sms.project.plandetails.controller;

import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import com.sms.project.plandetails.service.PlanService;
import com.sms.project.user.entity.User;
import com.sms.project.user.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plan/v1")
@Slf4j
public class PlanController {
    @Autowired
    private PlanService planService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAnyRole('ROLE_WRITER', 'ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<PlanDto>> listOfPlan() {
        List<PlanDto> list = planService.listOfPlans();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_WRITER', 'ROLE_ADMIN')")
    @GetMapping("/getPlansByType")
    public List<PlanDto> listOfPlanBasedOnType() {
        User userInfo = getLoginUser();
        String strRole = userInfo.getRole();
        String[] split = strRole.split("_");
        String planType = null;
        if (split[split.length - 1].equalsIgnoreCase("b2b")) {
            planType = "b2b";
        }
        if (split[split.length - 1].equalsIgnoreCase("b2c")) {
            planType = "b2c";
        }
        List<PlanDetail> planList = planService.listOfPlanBasedOnUser(planType);
        return planList.stream().map(planDetail -> modelMapper.map(planDetail,PlanDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_WRITER')")
    @PostMapping("/create")
    public ResponseEntity<PlanDto> createNewPlan(@RequestParam int productId, @RequestBody PlanDto newPlan) {
        return new ResponseEntity<>(planService.postNewPlan(productId, newPlan), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_WRITER', 'ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public String deletePlan(@RequestParam int planId) {
        planService.deletePlanByWriter(planId);
        return "Plan deleted";
    }

    @PreAuthorize("hasAnyRole('ROLE_WRITER', 'ROLE_ADMIN')")
    @PutMapping("/update/{planId}")
    public PlanDto planUpdate(@PathVariable int planId, @RequestBody PlanDto plan) {
        return planService.updatePlan(planId, plan);
    }

    public User getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepo.findByEmail(email).get();
    }

    @GetMapping("/getById")
    public PlanDto getPlan(@RequestParam int planId){
        return planService.getById(planId);
    }

}
