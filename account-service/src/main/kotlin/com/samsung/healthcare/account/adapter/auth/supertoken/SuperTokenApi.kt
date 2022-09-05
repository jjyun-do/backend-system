package com.samsung.healthcare.account.adapter.auth.supertoken

import feign.Headers
import feign.RequestLine
import reactor.core.publisher.Mono

@Headers("Accept: application/json")
interface SuperTokenApi {
    @RequestLine("POST /recipe/signin")
    @Headers("Content-Type: application/json")
    fun signIn(signInRequest: SignInRequest): Mono<SignInResponse>
}
