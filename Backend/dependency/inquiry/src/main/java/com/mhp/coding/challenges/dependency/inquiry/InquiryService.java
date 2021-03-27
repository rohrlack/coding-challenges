package com.mhp.coding.challenges.dependency.inquiry;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InquiryService {

    private static final Logger LOG = LoggerFactory.getLogger(InquiryService.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public void create(final Inquiry inquiry) {
        LOG.info("User sent inquiry: {}", inquiry);
        applicationEventPublisher.publishEvent(inquiry);
    }

}
