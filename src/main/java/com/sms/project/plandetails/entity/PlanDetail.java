package com.sms.project.plandetails.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sms.project.common.Entity.CommonEntity;
import com.sms.project.product.entity.Product;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@Table(name = "plan")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Where(clause = "deleted_at is null")
public class PlanDetail extends CommonEntity {
    private String planName;
    private String planTypeFor;
    private int price;
    private int validityDays;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public PlanDetail(int id, LocalDate deleted_at, String planName, String planTypeFor, int price, int validityDays) {
        super(id, deleted_at);
        this.planName = planName;
        this.planTypeFor = planTypeFor;
        this.price = price;
        this.validityDays = validityDays;
    }
}
