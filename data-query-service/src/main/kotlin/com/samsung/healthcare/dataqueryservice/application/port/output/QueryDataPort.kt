package com.samsung.healthcare.dataqueryservice.application.port.output

interface QueryDataPort {
    fun executeQuery(projectId: String, userId: String?, sql: String): QueryDataResult
}
