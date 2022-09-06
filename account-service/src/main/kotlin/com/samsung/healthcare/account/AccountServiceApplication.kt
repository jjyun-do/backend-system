package com.samsung.healthcare.account

import com.samsung.healthcare.account.application.config.ApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties::class)
class AccountServiceApplication

@SuppressWarnings("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<AccountServiceApplication>(*args)
}
