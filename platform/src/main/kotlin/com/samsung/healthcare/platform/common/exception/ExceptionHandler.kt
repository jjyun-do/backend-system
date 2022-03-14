package com.samsung.healthcare.platform.common.exception

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class ExceptionHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    configurer: ServerCodecConfigurer,
) : AbstractErrorWebExceptionHandler(errorAttributes, webProperties.resources, applicationContext) {
    init {
        super.setMessageReaders(configurer.readers)
        super.setMessageWriters(configurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all()) { request ->
            val errorAttributes: Map<String, Any> = getErrorAttributes(request, ErrorAttributeOptions.defaults())

            ServerResponse
                .status(errorAttributes[GlobalErrorAttributes.STATUS] as HttpStatus)
                .bodyValue(errorAttributes[GlobalErrorAttributes.MESSAGE] ?: "unexpected error occurred")
        }
}

class BadRequestException(override val message: String = "bad request") : RuntimeException(message)
class ForbiddenException(override val message: String = "forbidden") : RuntimeException(message)
class InternalServerException(override val message: String = "internal server error") : RuntimeException(message)
class NotFoundException(override val message: String = "not found") : RuntimeException(message)
class UnauthorizedException(override val message: String = "unauthorized") : RuntimeException(message)
