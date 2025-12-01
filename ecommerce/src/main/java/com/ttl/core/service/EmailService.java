package com.ttl.core.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        String subject = "Xác nhận đăng ký tài khoản";
        String confirmationUrl = "http://localhost:4200/verify?token=" + token; // link FE Angular
        String content = """
                <p>Chào bạn,</p>
                <p>Cảm ơn bạn đã đăng ký tài khoản.</p>
                <p>Vui lòng nhấp vào link dưới đây để xác thực email:</p>
                <p><a href="%s">Xác nhận tài khoản</a></p>
                <br>
                <p>Nếu bạn không đăng ký, vui lòng bỏ qua email này.</p>
                """.formatted(confirmationUrl);

        sendHtmlMessage(to, subject, content);
    }

    private void sendHtmlMessage(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = gửi dạng HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Không thể gửi email tới " + to, e);
        }
    }
}

