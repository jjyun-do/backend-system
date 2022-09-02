package com.samsung.healthcare.account.application.accesscontrol

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.account.domain.Role.ProjectRole
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import reactor.core.publisher.Mono

@Aspect
class AccessControlAspect {

    @Around("@annotation(Authorize)")
    fun withAuthorize(joinPoint: ProceedingJoinPoint): Any {
        return ContextHolder.getAccount()
            .log()
            .map { account -> checkRoles(account, joinPoint) }
            .onErrorMap { ex -> ex.printStackTrace(); IllegalAccessException() }
            .then(proceed(joinPoint))
    }

    private fun proceed(joinPoint: ProceedingJoinPoint): Mono<*> {
        val result = joinPoint.proceed()
        return if (result is Mono<*>) result
        else Mono.justOrEmpty(result)
    }

    private fun checkRoles(account: Account, joinPoint: ProceedingJoinPoint) {
        joinPoint.args.filterIsInstance(Collection::class.java)
            .flatten()
            .filterIsInstance(Role::class.java)
            .map { checkRole(account, it as ProjectRole) }
    }

    private fun checkRole(account: Account, role: ProjectRole) {
        if (!account.canAssignProjectRole(role.projectId))
            throw IllegalAccessException()
    }
}
