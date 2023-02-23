package com.samsung.healthcare.platform.adapter.web.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.module.kotlin.jacksonMapperBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {
    @Bean
    fun objectMapper(): ObjectMapper =
        jacksonMapperBuilder()
            .addModule(
                SimpleModule().apply {
                    addDeserializer(
                        Boolean::class.java,
                        CoercionBooleanDeserializer()
                    )
                }
            ).build()

    class CoercionBooleanDeserializer : JsonDeserializer<Boolean>() {
        override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Boolean {
            return when (p.currentToken) {
                JsonToken.VALUE_TRUE -> true
                JsonToken.VALUE_FALSE -> false
                else -> throw MismatchedInputException.from(p, Boolean::class.java, "invalid")
            }
        }
    }
}
