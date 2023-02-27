package com.sms.project.subcription.entity;

import com.sms.project.plandetails.entity.PlanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private int id;

    private String subscriptionStatus;

    private LocalDate subscriptionStartDate;

    private LocalDate subscriptionEndDate;

    private int subscriptionDuration;

    private float amount;

    private PlanDto planDto;
}
