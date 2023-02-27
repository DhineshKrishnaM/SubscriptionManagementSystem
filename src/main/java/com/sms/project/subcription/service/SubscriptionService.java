package com.sms.project.subcription.service;

import com.itextpdf.text.DocumentException;
import com.sms.project.exceptions.ProductException;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.entity.SubscriptionDto;
import com.sms.project.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.util.List;

@Service
public interface SubscriptionService {
    SubscriptionDto createSubscription(int planId, SubscriptionDto subscriptionDto) throws MessagingException, DocumentException, FileNotFoundException, ProductException;

    String deleteSubscription(int id);

    List<SubscriptionDto> getAllList();

    User getLoginUser();

    List<Subscription> getAllSubscriptionsList(int id);

    ResponseEntity<SubscriptionDto> getSubscriptionById(int subscriptionId);
}
