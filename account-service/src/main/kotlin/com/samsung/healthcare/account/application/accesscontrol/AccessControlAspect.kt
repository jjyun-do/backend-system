package com.samsung.healthcare.account.application.accesscontrol

import com.samsung.healthcare.account.application.context.ContextHolder
import com.samsung.healthcare.account.domain.Account
import com.samsung.healthcare.account.domain.Role
import com.samsung.healthcare.account.domain.Role.ProjectRole
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Component
@Aspect
class AccessControlAspect {

    @Around("@annotation(Authorize)")
    fun withAuthorize(joinPoint: ProceedingJoinPoint): Any {
        return ContextHolder.getAccount()
            .switchIfEmpty { Mono.error(IllegalAccessException()) }
            .map { account -> checkRoles(account, joinPoint) }
            .onErrorMap { ex -> ex.printStackTrace(); IllegalAccessException() }
            .then(proceed(joinPoint))
    }

    private fun proceed(joinPoint: ProceedingJoinPoint): Mono<*> {
        return Mono.defer {
            joinPoint.proceed() as Mono<*>
        }
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
