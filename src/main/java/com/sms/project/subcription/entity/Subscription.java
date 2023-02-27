package com.sms.project.subcription.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.user.entity.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Table(name = "subscription")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subscription extends CommonEntity {

    private String subscriptionStatus;

    private LocalDate subscriptionStartDate;

    private LocalDate subscriptionEndDate;

    private int subscriptionDuration;

    private float amount;

    @ManyToOne
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private PlanDetail planDetail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
