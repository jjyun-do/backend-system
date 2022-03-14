package com.samsung.healthcare.platform.common.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfig(
    private val config: ApplicationProperties,
) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway = Flyway(
        Flyway.configure().baselineOnMigrate(true).dataSource(
            "jdbc:${config.db.url}", config.db.user, config.db.password
        )
    )
}
