package com.example.passidapp

import com.example.passidapp.models.AccessLog
import com.example.passidapp.network.ApiService
import com.example.passidapp.repository.AccessLogRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.UUID

class AccessLogRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var accessLogRepository: AccessLogRepository
    private val gson: Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
        .create()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(ApiService::class.java)
        accessLogRepository = AccessLogRepository(apiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetAccessLog() = runBlocking {
        val logId = UUID.randomUUID()
        val accessLog = AccessLog(
            logId = logId,
            userId = UUID.randomUUID(),
            passId = UUID.randomUUID(),
            accessDatetime = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
            accessType = "ENTRY"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(accessLog))
                .addHeader("Content-Type", "application/json")
        )

        val result = accessLogRepository.getAccessLog(logId)
        assertEquals(accessLog, result)
    }

    @Test
    fun testCreateAccessLog() = runBlocking {
        val accessLog = AccessLog(
            logId = null,
            userId = UUID.randomUUID(),
            passId = UUID.randomUUID(),
            accessDatetime = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
            accessType = "ENTRY"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(accessLog))
                .addHeader("Content-Type", "application/json")
        )

        val result = accessLogRepository.createAccessLog(accessLog)
        assertEquals(accessLog.userId, result.userId)
        assertEquals(accessLog.passId, result.passId)
        assertEquals(accessLog.accessDatetime, result.accessDatetime)
        assertEquals(accessLog.accessType, result.accessType)
    }
}
