package com.samsung.healthcare.account.application.service

import com.samsung.healthcare.account.POSITIVE_TEST
import com.samsung.healthcare.account.application.config.InvitationProperties
import com.samsung.healthcare.account.domain.Email
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.mail.javamail.JavaMailSender
import reactor.test.StepVerifier
import javax.mail.Session
import javax.mail.internet.MimeMessage

internal class MailServiceTest {
    private val mailSender = mockk<JavaMailSender>()

    private val invitationProperties = InvitationProperties("test")

    private val mailService = MailService(mailSender, invitationProperties)

    @Test
    @Tag(POSITIVE_TEST)
    fun `sendMail should not emit Event`() {
        every { mailSender.send(any<MimeMessage>()) } returns Unit
        every { mailSender.createMimeMessage() } returns MimeMessage(null as Session?)

        val email = Email("test@reserach-hub.test.com")
        val resetToken = "reset-token"

        StepVerifier.create(
            mailService.sendMail(email, resetToken)
        ).verifyComplete()
    }
}
