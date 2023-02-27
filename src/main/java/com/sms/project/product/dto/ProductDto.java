package com.sms.project.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.plandetails.entity.PlanDetail;
import com.sms.project.plandetails.entity.PlanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private int id;
    private String productName;
    private String description;
    private int storage;
    @JsonIgnoreProperties
    private List<PlanDto> planDto;
}
