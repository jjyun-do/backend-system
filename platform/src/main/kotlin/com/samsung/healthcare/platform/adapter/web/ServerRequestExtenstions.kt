package com.samsung.healthcare.platform.adapter.web

import org.springframework.web.reactive.function.server.ServerRequest

fun ServerRequest.getId(): String = this.pathVariable("id")
