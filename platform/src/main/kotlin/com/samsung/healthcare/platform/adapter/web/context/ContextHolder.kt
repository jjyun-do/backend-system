package com.samsung.healthcare.platform.adapter.web.context

import reactor.core.publisher.Mono
import reactor.util.context.Context

object ContextHolder {
    private const val PROJECT_ID = "PROJECT_ID"

    fun setProjectId(id: String) =
        Context.of(PROJECT_ID, Mono.just(id))

    fun getProjectId(): Mono<String> =
        Mono.deferContextual {
            it.getOrEmpty<Mono<String>>(PROJECT_ID)
                .orElseGet { Mono.empty() }
        }
}
