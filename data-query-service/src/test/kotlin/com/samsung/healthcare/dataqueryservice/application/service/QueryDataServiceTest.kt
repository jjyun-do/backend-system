package com.samsung.healthcare.dataqueryservice.application.service

import com.samsung.healthcare.dataqueryservice.application.port.input.QueryDataCommand
import com.samsung.healthcare.dataqueryservice.application.port.input.QueryDataResultSet
import com.samsung.healthcare.dataqueryservice.application.port.input.QueryDataResultSet.MetaData
import com.samsung.healthcare.dataqueryservice.application.port.output.QueryDataPort
import com.samsung.healthcare.dataqueryservice.application.port.output.QueryDataResult
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag

internal class QueryDataServiceTest {
    private val queryDataPort = mockk<QueryDataPort>()

    private val queryDataService = QueryDataService(queryDataPort)

    @Test
    @Tag("positive")
    fun `execute should not emit event`() {
        every { queryDataPort.executeQuery(any(), any(), any()) } returns QueryDataResult(emptyList(), emptyList())

        val expectedResult = QueryDataResultSet(MetaData(emptyList(), 0), emptyList())
        val actualResult = queryDataService.execute("project-id", "account-id", QueryDataCommand("sql"))

        assertEquals(expectedResult, actualResult)
    }
}
