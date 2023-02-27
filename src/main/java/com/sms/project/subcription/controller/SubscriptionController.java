package com.sms.project.subcription.controller;

import com.itextpdf.text.DocumentException;
import com.sms.project.exceptions.ProductException;
import com.sms.project.subcription.entity.SubscriptionDto;
import com.sms.project.subcription.repository.SubscriptionRepo;
import com.sms.project.subcription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
    @Autowired
    SubscriptionService subscription;
    @Autowired
    private SubscriptionRepo subscriptionRepo;

    @PreAuthorize("hasAnyRole('ROLE_B2B', 'ROLE_B2C')")
    @PostMapping("/v1/create")
    public SubscriptionDto newSubscription(@RequestParam int planId, @RequestBody SubscriptionDto subscriptionDto) throws MessagingException, DocumentException, FileNotFoundException, ProductException {
        return subscription.createSubscription(planId, subscriptionDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/v1/delete/{id}")
    public ResponseEntity<String> removeSubscription(@PathVariable("id") int id) {
        subscription.deleteSubscription(id);
        return new ResponseEntity<>("Subscription removed for this product with your plan", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/v1/getAll", produces = "application/json")
    public ResponseEntity<List<SubscriptionDto>> get() {
        return new ResponseEntity<>(subscription.getAllList(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/v1/getById")
    public ResponseEntity<SubscriptionDto> getSubscription(@RequestParam int subscriptionId){
        return subscription.getSubscriptionById(subscriptionId);
    }

}

