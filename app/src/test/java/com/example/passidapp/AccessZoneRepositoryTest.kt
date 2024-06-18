package com.example.passidapp.repository

import com.example.passidapp.models.AccessZone
import com.example.passidapp.network.ApiService
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
import java.util.UUID

class AccessZoneRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var accessZoneRepository: AccessZoneRepository
    private val gson: Gson = GsonBuilder().create()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(ApiService::class.java)
        accessZoneRepository = AccessZoneRepository(apiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetAccessZone() = runBlocking {
        val zoneId = UUID.randomUUID()
        val accessZone = AccessZone(
            zoneId = zoneId,
            zoneName = "Main Entrance",
            description = "Main entrance of the building"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(accessZone))
                .addHeader("Content-Type", "application/json")
        )

        val result = accessZoneRepository.getAccessZone(zoneId)
        assertEquals(accessZone, result)
    }

    @Test
    fun testCreateAccessZone() = runBlocking {
        val accessZone = AccessZone(
            zoneId = null,
            zoneName = "Main Entrance",
            description = "Main entrance of the building"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(accessZone))
                .addHeader("Content-Type", "application/json")
        )

        val result = accessZoneRepository.createAccessZone(accessZone)
        assertEquals(accessZone.zoneName, result.zoneName)
        assertEquals(accessZone.description, result.description)
    }

    @Test
    fun testDeleteAccessZone() = runBlocking {
        val zoneId = UUID.randomUUID()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(204) // No Content
        )

        val response = accessZoneRepository.deleteAccessZone(zoneId)
        assertEquals(true, response.isSuccessful)
    }

    @Test
    fun testUpdateAccessZone() = runBlocking {
        val zoneId = UUID.randomUUID()
        val updatedAccessZone = AccessZone(
            zoneId = zoneId,
            zoneName = "Updated Entrance",
            description = "Updated entrance of the building"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(updatedAccessZone))
                .addHeader("Content-Type", "application/json")
        )

        val result = accessZoneRepository.updateAccessZone(zoneId, updatedAccessZone)
        assertEquals(updatedAccessZone.zoneName, result.zoneName)
        assertEquals(updatedAccessZone.description, result.description)
    }
}
