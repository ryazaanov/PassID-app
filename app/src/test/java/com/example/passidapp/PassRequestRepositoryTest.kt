package com.example.passidapp

import com.example.passidapp.models.PassRequest
import com.example.passidapp.network.ApiService
import com.example.passidapp.repository.PassRequestRepository
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

class PassRequestRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var passRequestRepository: PassRequestRepository
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(ApiService::class.java)
        passRequestRepository = PassRequestRepository(apiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetPassRequest() = runBlocking {
        val requestId = UUID.randomUUID()
        val passRequest = PassRequest(
            requestId = requestId,
            userId = UUID.randomUUID(),
            requestDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
            requestStatus = "PENDING",
            approved = false,
            adminId = null
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(passRequest))
                .addHeader("Content-Type", "application/json")
        )

        val result = passRequestRepository.getPassRequest(requestId)
        assertEquals(passRequest, result)
    }

    @Test
    fun testCreatePassRequest() = runBlocking {
        val passRequest = PassRequest(
            requestId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            requestDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
            requestStatus = "PENDING",
            approved = false,
            adminId = null
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(passRequest))
                .addHeader("Content-Type", "application/json")
        )

        val result = passRequestRepository.createPassRequest(passRequest)
        assertEquals(passRequest, result)
    }

    @Test
    fun testApprovePassRequest() = runBlocking {
        val requestId = UUID.randomUUID()
        val passRequest = PassRequest(
            requestId = requestId,
            userId = UUID.randomUUID(),
            requestDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
            requestStatus = "APPROVED",
            approved = true,
            adminId = UUID.randomUUID()
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(passRequest))
                .addHeader("Content-Type", "application/json")
        )

        val result = passRequestRepository.approvePassRequest(
            requestId = requestId,
            adminId = passRequest.adminId!!,
            zoneId = UUID.randomUUID(),
            passType = "TEMPORARY",
            accessLevel = 3
        )
        assertEquals(passRequest, result)
    }

    @Test
    fun testRejectPassRequest() = runBlocking {
        val requestId = UUID.randomUUID()
        val passRequest = PassRequest(
            requestId = requestId,
            userId = UUID.randomUUID(),
            requestDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
            requestStatus = "REJECTED",
            approved = false,
            adminId = UUID.randomUUID()
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(passRequest))
                .addHeader("Content-Type", "application/json")
        )

        val result = passRequestRepository.rejectPassRequest(
            requestId = requestId,
            adminId = passRequest.adminId!!
        )
        assertEquals(passRequest, result)
    }
}
