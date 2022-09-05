package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.domain.Email
import org.springframework.mail.MailSender
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MailService(
    private val mailSender: MailSender
) {
    internal fun sendMail(email: Email, resetToken: String): Mono<Void> {
        TODO()
    }
}
