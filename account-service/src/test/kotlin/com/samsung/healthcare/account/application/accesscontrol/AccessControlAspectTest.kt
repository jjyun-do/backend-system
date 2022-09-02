package com.samsung.healthcare.account.application.accesscontrol

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.application.port.input.AccountServicePort
import com.samsung.healthcare.account.application.service.AccountService
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.account.domain.Role.ProjectRole.ProjectOwner
import com.samsung.healthcare.account.domain.Role.ProjectRole.Researcher
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory
import reactor.core.publisher.Mono
import reactor.kotlin.test.verifyError
import reactor.test.StepVerifier

internal class AccessControlAspectTest {

    private val accountServiceMock = mockk<AccountService>()

    private val accountService = AspectJProxyFactory(accountServiceMock).apply {
        addAspect(AccessControlAspect())
    }.getProxy<AccountServicePort>()

    @Test
    fun `should throw IllegalAccessException when account does not have a owner roles for project`() {
        every { accountServiceMock.inviteUser(any(), any()) } returns Mono.empty()

        val projectId = "project-id"
        StepVerifier.create(
            withAccountContext(
                accountService.inviteUser(Email("test@test.com"), listOf(Researcher(projectId))),
                testAccount(Researcher(projectId))
            )
        ).verifyError<IllegalAccessException>()
    }

    @Test
    fun `should throw IllegalAccessException when account does have project owner roles and assign researcher`() {
        val projectId = "project-id"
        val email = Email("test@test.com")
        val roles = listOf(Researcher(projectId))

        every { accountServiceMock.inviteUser(email, roles) } returns Mono.empty()

        StepVerifier.create(
            withAccountContext(
                accountService.inviteUser(email, roles),
                testAccount(ProjectOwner(projectId))
            )
        ).verifyComplete()

        verify { accountServiceMock.inviteUser(email, roles) }
    }

    @Test
    fun `should throw IllegalAccessException when context has no account`() {
        every { accountServiceMock.inviteUser(any(), any()) } returns Mono.empty()
        StepVerifier.create(
            accountService.inviteUser(Email("test@test.com"), emptyList())
        ).verifyError<IllegalAccessException>()
    }

    private fun withAccountContext(mono: Mono<*>, account: Account) =
        ContextHolder.setAccount(mono, account)

    private fun testAccount(vararg roles: Role) =
        Account("account", Email("test@test.com"), roles.asList())
}
