package com.sms.project.subcription.repository;

import com.sms.project.product.entity.Product;
import com.sms.project.subcription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription,Integer> {
//    @Query(value = "select New com.sms.project.subcription.entity.Subscription(u.id,u.deleted_at,u.endDate,u.startDate,u.status) from Subscription as u")
    List<Subscription> findAll();
//   @Query(value = "select *  from product pro,(select product_id from subscription where user_id=1 and subscription_status='active') as prod where pro.id=prod.product_id",nativeQuery = true)
    @Query(value = "select * from product pro where pro.id in (select product_id from subscription where user_id=1 and subscription_status='active') ;",nativeQuery = true)
    Object[] findProductByUserId(int userId);

    @Query(value = "select * from subscription where user_id=?;",nativeQuery = true)
    List<Subscription> findList(int id);
}
