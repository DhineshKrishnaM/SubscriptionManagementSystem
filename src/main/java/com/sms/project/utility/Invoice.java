package com.sms.project.utility;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.text.DocumentException;
import com.sms.project.subcription.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class Invoice {
    private static final String FILE_NAME = "Invoice.pdf";

    @Autowired
    private JavaMailSender javaMailSender;

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Course Name", "Start Date", "End Date", "Plan Name", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public void generateUsersPDF(Subscription subscription) throws MessagingException, FileNotFoundException, DocumentException {

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE_NAME));
            document.open();

            PdfPTable table = new PdfPTable(5);
            List<Subscription> subscriptions = new ArrayList<>();
            subscriptions.add(subscription);
            addTableHeader(table);
            addUserDetails(table, subscriptions);

            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setSize(18);
            Paragraph p = new Paragraph("Vinaa Incorporation..", font);
            document.add(Chunk.NEWLINE);
            Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font1.setSize(16);
            Paragraph p1 = new Paragraph("Invoice", font1);
            p.setAlignment(1);
            p1.setAlignment(1);

            document.add(p);
            document.add(Chunk.NEWLINE);
            document.add(p1);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Customer Name : " + subscriptions.get(0).getUser().getUsername().toUpperCase()));
            document.add(new Paragraph("Phone Number  :  xxxxxx9856"));
            document.add(new Paragraph("Payment Status: Paid"));
            String uniqueID = UUID.randomUUID().toString();
            document.add(new Paragraph("Invoice Id     : " + uniqueID));
            String date = String.valueOf(LocalDate.now());
            document.add(new Paragraph("Date of issued  : " + date));
            document.add(Chunk.NEWLINE);

            document.add(table);
            Paragraph paragraph = new Paragraph("             Total:      " + subscriptions.get(0).getPlanDetail().getPrice());
            paragraph.setAlignment(1);
            document.add(paragraph);
            document.close();

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(subscription.getUser().getEmail());
            helper.setSubject("Subscription Details.");
            helper.setText("Invoice copy for current subscription.");
            helper.addAttachment(FILE_NAME, new File(FILE_NAME));
            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addUserDetails(PdfPTable table, List<Subscription> subscriptions) {
        for (Subscription subs : subscriptions) {
            table.addCell(String.valueOf(subs.getPlanDetail().getProduct().getProductName()));
            table.addCell(String.valueOf(subs.getSubscriptionStartDate()));
            table.addCell(String.valueOf(subs.getSubscriptionEndDate()));
            table.addCell(subs.getPlanDetail().getPlanName());
            table.addCell(String.valueOf(subs.getPlanDetail().getPrice()));
        }

    }

}