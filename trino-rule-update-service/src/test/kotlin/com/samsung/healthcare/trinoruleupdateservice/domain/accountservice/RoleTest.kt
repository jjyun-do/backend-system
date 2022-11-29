package com.samsung.healthcare.trinoruleupdateservice.domain.accountservice

import com.samsung.healthcare.trinoruleupdateservice.NEGATIVE_TEST
import com.samsung.healthcare.trinoruleupdateservice.POSITIVE_TEST
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

internal class RoleTest {

    @Test
    @Tag(POSITIVE_TEST)
    fun `newRole should work properly`() {
        val projectId = "some-id"
        val position = "some-position"

        val role1 = Role.newRole("team-admin")
        val role2 = Role.newRole("service-account")
        val role3 = Role.newRole("$projectId:$position")

        assertEquals("team-admin", role1.position)
        assertNull(role1.projectId)

        assertEquals("service-account", role2.position)
        assertNull(role2.projectId)

        assertEquals(position, role3.position)
        assertEquals(projectId, role3.projectId)
    }

    @Test
    @Tag(NEGATIVE_TEST)
    fun `newRole should fail on position with no colon`() {
        val positionWithNoColon = "position"
        assertThrows<IndexOutOfBoundsException> { Role.newRole(positionWithNoColon) }
    }
}
