package com.mhp.coding.challenges.dependency.notifications;

import com.mhp.coding.challenges.dependency.inquiry.Inquiry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(InquiryEventListener.class);

    private final EmailHandler emailHandler;
    private final PushNotificationHandler pushNotificationHandler;

    @EventListener
    public void onApplicationEvent(Inquiry inquiry) {
        LOG.info("received inquiry {}", inquiry);
        emailHandler.sendEmail(inquiry);
        pushNotificationHandler.sendNotification(inquiry);
    }
}
