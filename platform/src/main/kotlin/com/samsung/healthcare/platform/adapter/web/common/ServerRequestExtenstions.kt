package com.samsung.healthcare.platform.adapter.web.common

import org.springframework.web.reactive.function.server.ServerRequest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun ServerRequest.getProjectId(): String = this.pathVariable("projectId")

fun ServerRequest.getStartTime(): LocalDateTime? = this.queryParam("start_time").map {
    LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}.orElse(null)

fun ServerRequest.getEndTime(): LocalDateTime? = this.queryParam("end_time").map {
    LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}.orElse(null)
