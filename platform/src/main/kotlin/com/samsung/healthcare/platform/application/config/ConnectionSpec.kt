package com.samsung.healthcare.platform.application.config

data class ConnectionSpecs(
    val specs: Map<String, ConnectionSpec> = mapOf(),
)

data class ConnectionSpec(
    val host: String,
    val port: Int,
    val db: String,
    val schema: String,
    val username: String,
    val password: String
)
