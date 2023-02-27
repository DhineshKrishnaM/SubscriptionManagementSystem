package com.sms.project.plandetails.entity;

import com.sms.project.product.dto.ProductDto;
import com.sms.project.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PlanDto {
    private int id;
    private String planName;
    private String planTypeFor;
    private int price;
    private int validityDays;
}
