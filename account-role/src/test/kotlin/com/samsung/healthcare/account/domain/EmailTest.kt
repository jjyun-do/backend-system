package com.samsung.healthcare.account.domain

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class EmailTest {

    @Test
    @Tag("negative")
    fun `Email should throw IllegalArgumentException when email start with dot`() {
        assertThrows<IllegalArgumentException> { Email(".123@com") }
    }

    @Test
    @Tag("negative")
    fun `Email should throw IllegalArgumentException when email start with @`() {
        assertThrows<IllegalArgumentException> { Email("@test.com") }
    }

    @Test
    @Tag("negative")
    fun `Email should throw IllegalArgumentException when email has no @`() {
        assertThrows<IllegalArgumentException> { Email("without-at.test.com") }
    }

    @Test
    @Tag("negative")
    fun `Email should throw IllegalArgumentException when email has not allowed character`() {
        assertThrows<IllegalArgumentException> { Email("test!@test.com") }
    }

    @Test
    @Tag("negative")
    fun `Email should throw IllegalArgumentException when email has no domain`() {
        assertThrows<IllegalArgumentException> { Email("test@test") }
    }

    @ParameterizedTest
    @ValueSource(strings = ["123@abc.com", "12-ab@abc.com"])
    @Tag("positive")
    fun `Email should return instance when email is valid`(value: String) {
        assertDoesNotThrow { Email(value) }
    }
}
