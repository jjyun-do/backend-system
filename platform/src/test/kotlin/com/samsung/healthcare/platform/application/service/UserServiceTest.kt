package com.samsung.healthcare.platform.application.service

import com.samsung.healthcare.platform.application.port.input.RegisterUserCommand
import com.samsung.healthcare.platform.application.port.output.UserOutputPort
import com.samsung.healthcare.platform.domain.Email
import com.samsung.healthcare.platform.domain.User
import com.samsung.healthcare.platform.domain.User.UserId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class UserServiceTest {
    private val userOutputPort = mockk<UserOutputPort>()
    private val userService = UserService(userOutputPort)
    private val testEmail = Email("test@abc.com")

    @Test
    fun `existsEmail should return true if already existed email`(): Unit = runBlocking {
        coEvery { userOutputPort.existsByEmail(testEmail) } returns true
        assertTrue(userService.existsByEmail(testEmail))
    }

    @Test
    fun `existsEmail should return false if not existed email`(): Unit = runBlocking {
        coEvery { userOutputPort.existsByEmail(testEmail) } returns false
        assertFalse(userService.existsByEmail(testEmail))
    }

    @Test
    fun `registerUser should return new userId`(): Unit = runBlocking {
        val user = User.newUser(testEmail, "sub", "provider")
        val userId = UserId.from(1)
        coEvery { userOutputPort.insert(user) } returns userId
        assertEquals(
            userId,
            userService.registerUser(RegisterUserCommand(user.email, user.sub, user.provider))
        )
    }
}
