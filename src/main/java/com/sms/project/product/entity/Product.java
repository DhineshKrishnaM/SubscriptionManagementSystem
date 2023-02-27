package com.sms.project.product.entity;

import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.plandetails.entity.PlanDetail;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Table(name = "product")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Where(clause = "deleted_at is null")
public class Product extends CommonEntity {
    private String productName;
    private String description;
    private int storage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<PlanDetail> planDetailList;

}
