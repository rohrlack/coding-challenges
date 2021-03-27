package com.mhp.coding.challenges.dependency.inquiry

import org.springframework.context.ApplicationEvent

class InquiryEvent(
    source: Any,
    val inquiry: Inquiry,
) : ApplicationEvent(source)
