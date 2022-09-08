package com.samsung.healthcare.platform.application.aop

@Target(AnnotationTarget.FUNCTION)
annotation class Requires(val roles: Array<String>)
