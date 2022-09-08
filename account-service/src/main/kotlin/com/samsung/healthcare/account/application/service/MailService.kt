package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.application.config.InvitationProperties
import com.samsung.healthcare.account.domain.Email
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

@Service
class MailService(
    private val mailSender: JavaMailSender,
    private val invitationProperties: InvitationProperties
) {

    companion object {
        // TODO use html template?
        private const val MESSAGE_TEMPLATE = """
            Please activiate your account from <a href="%s">%s</a>
        """
    }

    internal fun sendMail(email: Email, resetToken: String): Mono<Void> {
        Mono.fromCallable {
            mailSender.send(
                mailSender.createMimeMessage().apply {
                    MimeMessageHelper(this, true, "UTF-8")
                        .apply {
                            setTo(email.value)
                            // TODO
                            setSubject("Account activation request")
                            setText(htmlMessage(email, resetToken), true)
                        }
                }
            )
            // TODO config repeat
        }.retry(3)
            .subscribeOn(Schedulers.boundedElastic())
            .doOnError { it.printStackTrace() }
            .subscribe()

        return Mono.empty()
    }

    private fun htmlMessage(email: Email, resetToken: String): String =
        "${invitationProperties.url}?reset-token=$resetToken&email=${email.value}".let { path ->
            MESSAGE_TEMPLATE.format(path, path)
        }
}
