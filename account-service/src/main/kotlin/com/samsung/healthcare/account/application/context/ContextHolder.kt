package com.samsung.healthcare.account.application.context

import com.samsung.healthcare.account.domain.Account
import reactor.core.publisher.Mono
import reactor.util.context.Context
import reactor.util.context.ContextView

object ContextHolder {
    const val ACCOUNT_KEY = "account-key"

    fun getAccount(): Mono<Account> =
        Mono.deferContextual {
            Mono.fromCallable { it.getAccount() }
        }.log()

    fun <T> setAccount(mono: Mono<T>, account: Account) =
        mono.contextWrite(Context.of(ACCOUNT_KEY, account))
            .log()

    fun ContextView.getAccount(): Account =
        this.get<Account>(ACCOUNT_KEY)
}
