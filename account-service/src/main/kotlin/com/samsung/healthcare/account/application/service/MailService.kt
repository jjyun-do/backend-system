package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.domain.Email
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class MailService(
    private val mailSender: JavaMailSender
) {

    internal fun sendMail(email: Email, resetToken: String): Mono<Void> {
        mailSender.send(
            SimpleMailMessage().apply {
                setTo(email.value)
                setSubject("Invitation")

                // TODO
                setText(resetToken)
            }
        )
        return Mono.empty()
    }
}
