package com.samsung.healthcare.account.adapter.auth.supertoken

import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import com.marcinziolo.kotlin.wiremock.equalTo
import com.marcinziolo.kotlin.wiremock.post
import com.marcinziolo.kotlin.wiremock.returns
import com.marcinziolo.kotlin.wiremock.returnsJson
import com.samsung.healthcare.account.application.exception.SignInException
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Email
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import reactivefeign.utils.HttpStatus
import reactivefeign.webclient.WebReactiveFeign
import reactor.kotlin.test.verifyError
import reactor.test.StepVerifier
import java.util.UUID

internal class SuperTokenAdapterTest {
    @JvmField
    @RegisterExtension
    var wm = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort())
        .build()

    private val superTokenAdapter by lazy {
        SuperTokenAdapter(
            WebReactiveFeign.builder<SuperTokenApi>()
                .target(SuperTokenApi::class.java, "localhost:${wm.port}")
        )
    }

    private val email = Email("cubist@research-hub.tset.com")
    private val id = UUID.randomUUID().toString()

    @Test
    fun `signIn should return account when supertoken return ok`() {

        wm.post {
            url equalTo "/recipe/signin"
        } returnsJson {
            body =
                """{
    "status": "OK",
    "user": {
        "email": "${email.value}",
        "id": "$id",
        "timeJoined": 1659407200104
    }
}"""
        }

        StepVerifier.create(
            superTokenAdapter.signIn(email, "secret")
        ).expectNext(Account(id, email, emptyList()))
            .verifyComplete()
    }

    @Test
    fun `signIn should throw exception when supertoken return 404`() {
        wm.post {
            url equalTo "/recipe/signin"
        } returns {
            this.statusCode = HttpStatus.SC_NOT_FOUND
        }

        StepVerifier.create(
            superTokenAdapter.signIn(email, "secret")
        ).verifyError<SignInException>()
    }
}
