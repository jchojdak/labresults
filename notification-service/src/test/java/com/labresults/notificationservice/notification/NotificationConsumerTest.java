package com.labresults.notificationservice.notification;

import com.labresults.notificationservice.mail.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationConsumerTest {

    private static final String TEST_MAIL = "test@example.com";
    private static final String TEST_SUBJECT = "Test Subject";
    private static final String TEST_MESSAGE = "Test Message";
    private static final String EMPTY_STRING = "";

    @Mock
    private MailService mailService;

    @InjectMocks
    private NotificationConsumer notificationConsumer;

    private NotificationRequest notificationRequest;

    @BeforeEach
    void setUp() {
        notificationRequest = NotificationRequest.builder()
                .mail(TEST_MAIL)
                .subject(TEST_SUBJECT)
                .message(TEST_MESSAGE)
                .build();
    }

    @Test
    void receiveMessage_sendsMailSuccessfully() {
        notificationConsumer.receiveMessage(notificationRequest);
        verify(mailService, times(1)).send(TEST_MAIL, TEST_SUBJECT, TEST_MESSAGE);
    }

    @Test
    void receiveMessage_withEmptySubject_sendsMailSuccessfully() {
        notificationRequest.setSubject(EMPTY_STRING);
        notificationConsumer.receiveMessage(notificationRequest);
        verify(mailService, times(1)).send(TEST_MAIL, EMPTY_STRING, TEST_MESSAGE);
    }

    @Test
    void receiveMessage_withEmptyMessage_sendsMailSuccessfully() {
        notificationRequest.setMessage(EMPTY_STRING);
        notificationConsumer.receiveMessage(notificationRequest);
        verify(mailService, times(1)).send(TEST_MAIL, TEST_SUBJECT, EMPTY_STRING);
    }

    @Test
    void receiveMessage_withNullValues_sendsMailSuccessfully() {
        notificationRequest = NotificationRequest.builder()
                .mail(null)
                .subject(null)
                .message(null)
                .build();
        notificationConsumer.receiveMessage(notificationRequest);
        verify(mailService, times(1)).send(null, null, null);
    }
}
