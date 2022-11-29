package com.samsung.healthcare.trinoruleupdateservice.domain.trino.rule

import com.samsung.healthcare.trinoruleupdateservice.POSITIVE_TEST
import com.samsung.healthcare.trinoruleupdateservice.domain.accountservice.User
import com.samsung.healthcare.trinoruleupdateservice.domain.trino.rule.Rule.Companion.newRule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertNotNull

internal class RuleTest {

    @Test
    @Tag(POSITIVE_TEST)
    fun `newRule should not emit event`() {
        val owner = User("owner-id", "owner@research-hub.test.com", listOf("1:project-owner"))
        val headResearcher = User("head-id", "head@research-hub.test.com", listOf("1:head-researcher"))
        val researcher = User("researcher-id", "researcher@research-hub.test.com", listOf("1:researcher"))
        val prefix = "prefix"
        val postfix = "postfix"

        val users = listOf(owner, headResearcher, researcher)

        val rule = newRule(users, prefix, postfix)

        assertNotNull(rule.tables)
        assertFalse(rule.tables!!.filter { it.user == owner.id }.isEmpty())
        assertFalse(rule.tables!!.filter { it.user == headResearcher.id }.isEmpty())
        assertFalse(rule.tables!!.filter { it.user == researcher.id }.isEmpty())
    }
}
