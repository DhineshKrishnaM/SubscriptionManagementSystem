package com.sms.project.plandetails.repo;

import com.sms.project.plandetails.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository <PlanDetail,Integer> {

    @Query(value = "SELECT New com.sms.project.plandetails.entity.PlanDetail(u.id,u.deleted_at,u.planName,u.planTypeFor,u.price,u.validityDays) FROM PlanDetail as u WHERE u.planTypeFor = :planType")
    List<PlanDetail> findAllByPlanType(@Param("planType") String planType);
}
