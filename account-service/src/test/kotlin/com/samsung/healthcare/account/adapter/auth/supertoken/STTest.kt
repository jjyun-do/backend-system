package com.samsung.healthcare.account.adapter.auth.supertoken

import com.samsung.healthcare.account.application.port.output.JwtGenerationCommand
import com.samsung.healthcare.account.domain.Role.ProjectRole.HeadResearcher
import com.samsung.healthcare.account.domain.Role.ProjectRole.ProjectOwner
import com.samsung.healthcare.account.domain.Role.TeamAdmin
import org.junit.jupiter.api.Test
import reactivefeign.webclient.WebReactiveFeign

class STTest {

    @Test
    fun `listUser`() {
        val st = SuperTokenAdapter(
            WebReactiveFeign.builder<SuperTokensApi>()
                .target(SuperTokensApi::class.java, "http://10.113.65.59:3567")
        )

        val client = WebReactiveFeign.builder<SuperTokensApi>()
            .target(SuperTokensApi::class.java, "http://10.113.65.59:3567")

        val accounts = st.listUsers().block()!!
        accounts.forEach { println("listusers $it") }
    }

    @Test
    fun `generate  jwt token`() {
        val st = SuperTokenAdapter(
            WebReactiveFeign.builder<SuperTokensApi>()
                .target(SuperTokensApi::class.java, "http://10.113.65.59:3567")
        )

        val jwt = st.generateSignedJWT(
            JwtGenerationCommand(
                "research-hub.com",
                "subject-id",
                "cubist@samsug.com",
                listOf(
                    TeamAdmin, ProjectOwner("1"), HeadResearcher("2")
                ),
                86400
            )
        ).block()!!
        println("jwt token : $jwt")
    }
}
