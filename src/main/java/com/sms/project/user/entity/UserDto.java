package com.sms.project.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.entity.SubscriptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String username;
    private String email;
    private String password;
    private String role;
//    @JsonIgnore
    private List<SubscriptionDto> subscriptions;
}
