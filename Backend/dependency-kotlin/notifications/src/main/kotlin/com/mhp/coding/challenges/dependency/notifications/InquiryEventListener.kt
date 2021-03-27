package com.mhp.coding.challenges.dependency.notifications

import com.mhp.coding.challenges.dependency.inquiry.InquiryEvent
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class InquiryEventListener(
    @Autowired val emailHandler: EmailHandler,
    @Autowired val pushNotificationHandler: PushNotificationHandler
) {

    @EventListener
    fun onApplicationEvent(event: InquiryEvent) {
        logger.info { "Received InquiryEvent: $event" }
        emailHandler.sendEmail(event.inquiry)
        pushNotificationHandler.sendNotification(event.inquiry)
    }
}
