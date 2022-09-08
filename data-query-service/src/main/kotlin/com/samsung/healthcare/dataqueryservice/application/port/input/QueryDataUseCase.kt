package com.samsung.healthcare.dataqueryservice.application.port.input

interface QueryDataUseCase {
    fun execute(
        projectId: String,
        userId: String?,
        queryCommand: QueryDataCommand,
    ): QueryDataResultSet
}
