package com.samsung.healthcare.platform.logger.decorator

import mu.KotlinLogging
import com.samsung.healthcare.platform.logger.asString
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels

class LoggingRequestDecorator internal constructor(delegate: ServerHttpRequest) : ServerHttpRequestDecorator(delegate) {
    private val logger = KotlinLogging.logger {}
    private val body: Flux<DataBuffer>

    override fun getBody(): Flux<DataBuffer> = body

    init {
        if (logger.isInfoEnabled) {
            val path = delegate.uri.path
            val query = delegate.uri.query
            val method = (delegate.method ?: HttpMethod.GET).name
            val host = delegate.headers.host?.asString()
            logger.info(
                "{} {} | host: [{}]", method, path + (if (StringUtils.hasText(query)) "?$query" else ""), host
            )
            body = super.getBody().doOnNext { buffer: DataBuffer ->
                val bodyStream = ByteArrayOutputStream()
                Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
                logger.info("request: [{}]", String(bodyStream.toByteArray()).replace("\\s+".toRegex(), ""))
            }
        } else {
            body = super.getBody()
        }
    }
}
