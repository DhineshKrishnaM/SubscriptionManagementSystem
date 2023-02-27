package com.sms.project.ControllerTest;

import com.itextpdf.text.DocumentException;
import com.sms.project.exceptions.ProductException;
import com.sms.project.subcription.controller.SubscriptionController;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.entity.SubscriptionDto;
import com.sms.project.subcription.service.SubscriptionImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SubscriptionControllerTest {
    Subscription subscription=new Subscription();
    SubscriptionDto subscriptionDto=new SubscriptionDto();
    List<Subscription> list=new ArrayList<>();
    List<SubscriptionDto> list1=new ArrayList<>();
    @Mock
    SubscriptionImpl subscriptionImpl;
    @InjectMocks
    SubscriptionController subscriptionController;
    void setup(){
        subscription=Subscription.builder()
                .subscriptionStartDate(LocalDate.now())
                .subscriptionEndDate(LocalDate.now().plusDays(7))
                .subscriptionStatus("active")
                .subscriptionDuration(7)
                .build();
        subscriptionDto=SubscriptionDto.builder()
                .subscriptionStartDate(LocalDate.now())
                .subscriptionEndDate(LocalDate.now().plusDays(7))
                .subscriptionStatus("active")
                .subscriptionDuration(7)
                .build();
    }
    @DisplayName("To create new subscription")
    @Test
    void saveSubscription() throws MessagingException, DocumentException, FileNotFoundException, ProductException {
        Mockito.when(subscriptionImpl.createSubscription(1,subscriptionDto)).thenReturn(subscriptionDto);
        Assertions.assertThat(subscriptionController.newSubscription(1,subscriptionDto)).isEqualTo(subscriptionDto);
    }
    @Test
    void deleteSubscription(){
        Mockito.when(subscriptionImpl.deleteSubscription(1)).thenReturn("Subscription removed");
        Assertions.assertThat(subscriptionController.removeSubscription(1).getStatusCodeValue()).isEqualTo(200);
    }
    @Test
    void getAllSubscription(){
        Mockito.when(subscriptionImpl.getAllList()).thenReturn(list1);
        Assertions.assertThat(subscriptionController.get().getStatusCodeValue()).isEqualTo(200);
    }
}
