package com.labresults.notificationservice.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

    private static final String TEST_MAIL = "test@example.com";
    private static final String TEST_SUBJECT = "Test Subject";
    private static final String TEST_TEXT = "Test Text";
    private static final String EMPTY_STRING = "";

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private MailService mailService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void sendMailSuccessfully() {
        mailService.send(TEST_MAIL, TEST_SUBJECT, TEST_TEXT);
    }

    @Test
    void sendMailWithEmptyRecipient() {
        mailService.send(EMPTY_STRING, TEST_SUBJECT, TEST_TEXT);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendMailWithNullRecipient() {
        mailService.send(null, TEST_SUBJECT, TEST_TEXT);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendMailWithEmptySubject() {
        mailService.send(TEST_MAIL, EMPTY_STRING, TEST_TEXT);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendMailWithNullSubject() {
        mailService.send(TEST_MAIL, null, TEST_TEXT);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendMailWithEmptyText() {
        mailService.send(TEST_MAIL, TEST_SUBJECT, EMPTY_STRING);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendMailWithNullText() {
        mailService.send(TEST_MAIL, TEST_SUBJECT, null);

        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
