package com.sweaterbank.leasing.car.services;

import com.sweaterbank.leasing.car.controller.dto.responses.MailResponse;
import com.sweaterbank.leasing.car.exceptions.MailDataNotFoundException;
import com.sweaterbank.leasing.car.model.MailData;
import com.sweaterbank.leasing.car.repository.LeaseRepository;
import com.sweaterbank.leasing.car.repository.contants.MailSubjects;
import com.sweaterbank.leasing.car.repository.contants.Messages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class MailSenderService {
    @Value("${sweatbank-backend.reply-email}")
    private String REPLY_MAIL;
    private final JavaMailSender mailSender;
    private final LeaseRepository leaseRepository;

    public MailSenderService(JavaMailSender mailSender, LeaseRepository leaseRepository){
        this.mailSender = mailSender;
        this.leaseRepository = leaseRepository;
    }

    public MailResponse sendEmail(String applicationId, String to, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(REPLY_MAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        return new MailResponse(applicationId, subject, body,to ,LocalDateTime.now(ZoneId.of("GMT+3")));
    }

    public MailResponse sendRejectEmail(String to, String applicationId) throws MailDataNotFoundException {
        MailData mailData = getMailData(applicationId);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(REPLY_MAIL);
        message.setTo(to);
        message.setSubject(MailSubjects.NOTIFICATION_ABOUT_LEASE_APPLICATION);
        message.setText(
               Messages.REJECT_MAIL_MESSAGE.formatted(mailData.firstName(), mailData.lastName(), applicationId)
        );

        mailSender.send((message));
        return new MailResponse(applicationId, message.getSubject(), message.getText(),to ,LocalDateTime.now(ZoneId.of("GMT+3")));
    }

    public MailResponse sendApprovedEmail(String to, String applicationId) throws MailDataNotFoundException {
        MailData mailData = getMailData(applicationId);
        BigDecimal leasedAmount = mailData.carCost().subtract(mailData.downPayment());
        int leasingPeriodInMonths = mailData.leasingPeriod() * 12;



        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(REPLY_MAIL);
        message.setTo(to);
        message.setSubject(MailSubjects.NOTIFICATION_ABOUT_LEASE_APPLICATION);

        message.setText(
                Messages.APPROVED_MAIL_MESSAGE.formatted(
                        mailData.firstName(),
                        mailData.lastName(),
                        applicationId,
                        leasedAmount,
                        mailData.margin(),
                        mailData.euriborType(),
                        leasingPeriodInMonths
                )
        );

        mailSender.send((message));
        return new MailResponse(applicationId, message.getSubject(), message.getText(),to ,LocalDateTime.now(ZoneId.of("GMT+3")));
    }

    private MailData getMailData(String applicationId) throws MailDataNotFoundException {
        Optional<MailData> receivedData = leaseRepository.getDataForMail(applicationId);

        return receivedData.orElseThrow(() -> new MailDataNotFoundException(applicationId));
    }
}
