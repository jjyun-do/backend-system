package com.samsung.healthcare.dataqueryservice.adapter.web.interceptor

import com.samsung.healthcare.dataqueryservice.adapter.web.USER_ID_HEADER_NAME
import com.samsung.healthcare.dataqueryservice.application.context.AuthContext
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserIdInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        AuthContext.setValue(USER_ID_HEADER_NAME, request.getHeader(USER_ID_HEADER_NAME))
        return true
    }
}
