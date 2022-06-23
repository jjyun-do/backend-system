package com.samsung.healthcare.platform.adapter.web.filter

import com.samsung.healthcare.platform.application.config.Constants
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import reactor.util.context.Context

@Component
class ProjectIdWebFilter : WebFilter {
    override fun filter(serverWebExchange: ServerWebExchange, webFilterChain: WebFilterChain): Mono<Void> {
        var projectId = ""
        val elements = serverWebExchange.request.path.elements()
        for (i: Int in 0 until elements.size) {
            if (elements[i].value() == "projects" && i + 2 < elements.size) {
                projectId = elements[i + 2].value()
                break
            }
        }

        if (projectId.isBlank()) {
            return webFilterChain.filter(serverWebExchange)
        }
        return webFilterChain
            .filter(serverWebExchange)
            .contextWrite { ctx: Context ->
                ctx.put(
                    Constants.PROJECT_ID_KEY,
                    projectId,
                )
            }
    }
}
