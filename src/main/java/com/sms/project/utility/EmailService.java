package com.sms.project.utility;

import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.repository.SubscriptionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class EmailService {
    @Autowired
    SubscriptionRepo subscriptionRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PDFService pdfService;

    public void mailForMessage(String toEmail, String body, String subject) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("birunesh@gmail.com");
        simpleMailMessage.setTo("jonemelvin@gmail.com");
        simpleMailMessage.setText("Welcome");
        simpleMailMessage.setSubject("Hello");
        javaMailSender.send(simpleMailMessage);
        log.info("Mail sent to " + toEmail);
    }

    /****
     *
     * Scheduler for expiration mail and set the expiration as well
     */
    @Scheduled(cron = "0 0 0 * * *")
//    @Scheduled(fixedRate = 60000)
    public void checkExpiryOfAuctions() {
        log.info("Scheduled task Executing....!");
        List<Subscription> subscriptionList = subscriptionRepo.findAll();

        subscriptionList.stream().forEach(subscription -> {
            LocalDate localDate = LocalDate.now();
            if (localDate.equals(subscription.getSubscriptionEndDate().minusDays(2))) {
                try {
                    pdfService.generateUsersPDF(subscription.getUser().getEmail(), subscription.getUser().getUsername(), subscription.getUser().getId());
                } catch (MessagingException | FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //    @Scheduled(fixedRate = 180000)
    @Scheduled(cron = "0 0 0 * * *")
    public void setExpiration() {
        log.info("Expiration set executing....");

        List<Subscription> subscriptionList = subscriptionRepo.findAll();
        subscriptionList.stream().forEach(subscription -> {
            LocalDate local = LocalDate.now();
            if (local.equals(subscription.getSubscriptionEndDate())) {
                subscription.setSubscriptionStatus("Expired");
                subscriptionRepo.save(subscription);
            }
        });
    }

}
