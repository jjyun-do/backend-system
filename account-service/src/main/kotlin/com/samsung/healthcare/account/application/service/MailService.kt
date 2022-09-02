package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.domain.Email
import org.springframework.mail.MailSender
import reactor.core.publisher.Mono

class MailService(
    private val mailSender: MailSender
) {
    internal fun sendMail(email: Email, resetToken: String): Mono<Void> {
        TODO()
    }
}
