package com.bsujava.servlet.util;

import com.bsujava.servlet.database.DatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailSenderUtil {
    private static final Logger logger = LogManager.getLogger(EmailSenderUtil.class);

    private static final String FROM_EMAIL;
    private static final String LOGIN;
    private static final String PASSWORD;
    private static final String HOST;
    private static final String PORT;
    private static final Properties props;

    static {
        props = new Properties();
        try (InputStream input = EmailSenderUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Unable to find config.properties");
                throw new RuntimeException("Unable to find config.properties");
            }
            props.load(input);
            FROM_EMAIL = props.getProperty("mail.from");
            LOGIN = props.getProperty("mail.login");
            PASSWORD = props.getProperty("mail.password");
            HOST = props.getProperty("mail.smtp.host");
            PORT = props.getProperty("mail.smtp.port");

            // Set mail server properties
            props.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
            props.put("mail.smtp.starttls.enable", props.getProperty("mail.smtp.starttls.enable"));
            props.put("mail.smtp.host", HOST);
            props.put("mail.smtp.port", PORT);
            
            logger.info("Email configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading config.properties", e);
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    public static void sendConfirmationEmail(String recipientEmail, String token) throws MessagingException {
        

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(LOGIN, PASSWORD);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Confirm your registration");
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token;
        String htmlContent = "<html><body>"
                + "<h2>Please verify your email</h2>"
                + "<p>Click the link below to confirm your registration:</p>"
                + "<a href=\"" + confirmationUrl + "\">Verify Email</a>"
                + "<p>Or copy this link to your browser:</p>"
                + "<p>" + confirmationUrl + "</p>"
                + "</body></html>";
        message.setContent(htmlContent, "text/html");

        Transport.send(message);
    }
}
