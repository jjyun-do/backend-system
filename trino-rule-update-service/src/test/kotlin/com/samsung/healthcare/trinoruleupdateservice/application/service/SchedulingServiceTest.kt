package com.samsung.healthcare.trinoruleupdateservice.application.service

import com.samsung.healthcare.trinoruleupdateservice.POSITIVE_TEST
import com.samsung.healthcare.trinoruleupdateservice.application.config.ApplicationProperties
import com.samsung.healthcare.trinoruleupdateservice.application.config.ApplicationProperties.DatabaseConfig
import com.samsung.healthcare.trinoruleupdateservice.application.config.ApplicationProperties.AccessControlConfig
import com.samsung.healthcare.trinoruleupdateservice.application.config.ApplicationProperties.AccountServiceConfig
import com.samsung.healthcare.trinoruleupdateservice.application.config.ApplicationProperties.TrinoConfig
import com.samsung.healthcare.trinoruleupdateservice.application.port.output.GetUsersPort
import com.samsung.healthcare.trinoruleupdateservice.application.port.output.UpdateRulePort
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

internal class SchedulingServiceTest {
    private val getUsersPort = mockk<GetUsersPort>()

    private val updateRulePort = mockk<UpdateRulePort>()

    private val properties = ApplicationProperties(
        DatabaseConfig("prefix", "postfix"),
        AccountServiceConfig("localhost"),
        TrinoConfig(AccessControlConfig("/"))
    )

    private val schedulingService = SchedulingService(properties, getUsersPort, updateRulePort)

    @Test
    @Tag(POSITIVE_TEST)
    fun `fetchUsers should not emit event`() {
        every { getUsersPort.getUsers() } returns emptyList()
        every { updateRulePort.updateAccessControlConfigFile(any()) } returns Unit

        schedulingService.fetchUsers()
    }
}
