package com.samsung.healthcare.platform.application.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("config")
data class ApplicationProperties(
    val db: Db,
) {
    data class Db(
        val url: String,
        val user: String,
        val password: String,
    )
}
