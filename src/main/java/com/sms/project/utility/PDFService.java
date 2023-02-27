package com.sms.project.utility;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import com.sms.project.subcription.entity.Subscription;
import com.sms.project.subcription.service.SubscriptionImpl;
import com.sms.project.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PDFService {
    private static final String FILE_NAME = "SubscriptionExpire.pdf";
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SubscriptionImpl subscriptionService;


    private static void addTableHeader(PdfPTable table) {
        Stream.of("Duration", "StartDate", "EndDate", "PlanName")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.BLUE);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public void generateUsersPDF(String email, String userName, int id) throws MessagingException, FileNotFoundException, DocumentException {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("SubscriptionExpire.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(4);
            List<Subscription> subscriptions = subscriptionService.getAllSubscriptionsList(id);
            addTableHeader(table);
            addUserDetails(table, subscriptions);

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            Paragraph p = new Paragraph("Subscription Details", font);
            Paragraph p1 = new Paragraph("Your subscription should be expired within two days. We are kindly to request you to upgrade your plan. ");

            p.setAlignment(1);
            p1.setAlignment(0);
            document.add(p);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Dear " + userName + ":"));
            document.add(Chunk.NEWLINE);
            document.add(p1);
            document.add(new Paragraph(
                    "Thank you for being a part of our community. We hope you’ve been able to enjoy all the benefits of your membership these past few days.\n" +
                            "\n" +
                            "Great news! We’re running a one-time early renewal offer for all current members. Renew your membership right now to continue.\n" +
                            "\n" +
                            "RENEW NOW\n" +
                            "\n" +
                            "If you have any questions or suggestions, please don’t hesitate to reach out at vina@qbrain.com or +91 9632587412 to see how we can meet your needs. We’d love to hear your feedback!"));
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.close();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Plan Expiration Details.");
            helper.setText("Please find attached the list of user details.");
            helper.addAttachment("SubscriptionExpire.pdf", new File("SubscriptionExpire.pdf"));
            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addUserDetails(PdfPTable table, List<Subscription> subscriptions) {
        for (Subscription subs : subscriptions) {
            table.addCell(String.valueOf(subs.getSubscriptionDuration()));
            table.addCell(String.valueOf(subs.getSubscriptionStartDate()));
            table.addCell(String.valueOf(subs.getSubscriptionEndDate()));
            table.addCell(subs.getPlanDetail().getPlanName());
        }

    }

}