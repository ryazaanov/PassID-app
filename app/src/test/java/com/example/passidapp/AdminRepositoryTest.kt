package com.example.passidapp

import com.example.passidapp.models.Admin
import com.example.passidapp.network.ApiService
import com.example.passidapp.repository.AdminRepository
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

class AdminRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var adminRepository: AdminRepository
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
        adminRepository = AdminRepository(apiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetAdmin() = runBlocking {
        val adminId = UUID.randomUUID()
        val admin = Admin(
            adminId = adminId,
            userId = UUID.randomUUID(),
            position = "Manager",
            role = "Admin"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(admin))
                .addHeader("Content-Type", "application/json")
        )

        val result = adminRepository.getAdmin(adminId)
        assertEquals(admin, result)
    }

    @Test
    fun testCreateAdmin() = runBlocking {
        val admin = Admin(
            adminId = null,
            userId = UUID.randomUUID(),
            position = "Manager",
            role = "Admin"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(admin))
                .addHeader("Content-Type", "application/json")
        )

        val result = adminRepository.createAdmin(admin)
        assertEquals(admin.userId, result.userId)
        assertEquals(admin.position, result.position)
        assertEquals(admin.role, result.role)
    }

    @Test
    fun testDeleteAdmin() = runBlocking {
        val adminId = UUID.randomUUID()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(204) // No Content
        )

        val response = adminRepository.deleteAdmin(adminId)
        assertEquals(true, response.isSuccessful)
    }

    @Test
    fun testUpdateAdmin() = runBlocking {
        val adminId = UUID.randomUUID()
        val updatedAdmin = Admin(
            adminId = adminId,
            userId = UUID.randomUUID(),
            position = "Updated Manager",
            role = "Updated Admin"
        )

        mockWebServer.enqueue(
            MockResponse()
                .setBody(gson.toJson(updatedAdmin))
                .addHeader("Content-Type", "application/json")
        )

        val result = adminRepository.updateAdmin(adminId, updatedAdmin)
        assertEquals(updatedAdmin.userId, result.userId)
        assertEquals(updatedAdmin.position, result.position)
        assertEquals(updatedAdmin.role, result.role)
    }
}
