package com.mhp.coding.challenges.dependency.inquiry

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class InquiryService(
    @Autowired val applicationEventPublisher: ApplicationEventPublisher
) {
    fun create(inquiry: Inquiry) {
        logger.info {
            "User sent inquiry: $inquiry"
        }
        applicationEventPublisher.publishEvent(InquiryEvent(this, inquiry))
    }
}

data class Inquiry(
    var username: String,
    var recipient: String,
    var text: String,
)
