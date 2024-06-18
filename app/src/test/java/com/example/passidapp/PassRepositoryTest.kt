//package com.example.passidapp
//
//import com.example.passidapp.models.Pass
//import com.example.passidapp.network.ApiService
//import com.example.passidapp.repository.PassRepository
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import junit.framework.TestCase.assertEquals
//import kotlinx.coroutines.runBlocking
//import okhttp3.mockwebserver.MockResponse
//import okhttp3.mockwebserver.MockWebServer
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.text.SimpleDateFormat
//import java.util.UUID
//
//class PassRepositoryTest {
//
//    private lateinit var mockWebServer: MockWebServer
//    private lateinit var apiService: ApiService
//    private lateinit var passRepository: PassRepository
//    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()
//
//    @Before
//    fun setup() {
//        mockWebServer = MockWebServer()
//        mockWebServer.start()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl(mockWebServer.url("/"))
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
//        apiService = retrofit.create(ApiService::class.java)
//        passRepository = PassRepository(apiService)
//    }
//
//    @After
//    fun teardown() {
//        mockWebServer.shutdown()
//    }
//
//    @Test
//    fun testGetPass() = runBlocking {
//        val passId = UUID.randomUUID()
//        val pass = Pass(
//            passId = passId,
//            userId = UUID.randomUUID(),
//            zoneId = UUID.randomUUID(),
//            passType = "Regular",
//            issueDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
////            expiryDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-12-31"),
//            accessLevel = 1
//        )
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setBody(gson.toJson(pass))
//                .addHeader("Content-Type", "application/json")
//        )
//
//        val result = passRepository.getPass(passId)
//        assertEquals(pass, result)
//    }
//
//    @Test
//    fun testCreatePass() = runBlocking {
//        val pass = Pass(
//            passId = null,
//            userId = UUID.randomUUID(),
//            zoneId = UUID.randomUUID(),
//            passType = "Regular",
//            issueDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
//            expiryDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-12-31"),
//            accessLevel = 1
//        )
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setBody(gson.toJson(pass))
//                .addHeader("Content-Type", "application/json")
//        )
//
//        val result = passRepository.createPass(pass)
//        assertEquals(pass.userId, result.userId)
//        assertEquals(pass.zoneId, result.zoneId)
//        assertEquals(pass.passType, result.passType)
//        assertEquals(pass.issueDate, result.issueDate)
//        assertEquals(pass.expiryDate, result.expiryDate)
//        assertEquals(pass.accessLevel, result.accessLevel)
//    }
//
//    @Test
//    fun testDeletePass() = runBlocking {
//        val passId = UUID.randomUUID()
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setResponseCode(204) // No Content
//        )
//
//        val response = passRepository.deletePass(passId)
//        assertEquals(true, response.isSuccessful)
//    }
//
//    @Test
//    fun testUpdatePass() = runBlocking {
//        val passId = UUID.randomUUID()
//        val updatedPass = Pass(
//            passId = passId,
//            userId = UUID.randomUUID(),
//            zoneId = UUID.randomUUID(),
//            passType = "VIP",
//            issueDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"),
//            expiryDate = SimpleDateFormat("yyyy-MM-dd").parse("2023-12-31"),
//            accessLevel = 2
//        )
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setBody(gson.toJson(updatedPass))
//                .addHeader("Content-Type", "application/json")
//        )
//
//        val result = passRepository.updatePass(passId, updatedPass)
//        assertEquals(updatedPass.userId, result.userId)
//        assertEquals(updatedPass.zoneId, result.zoneId)
//        assertEquals(updatedPass.passType, result.passType)
//        assertEquals(updatedPass.issueDate, result.issueDate)
//        assertEquals(updatedPass.expiryDate, result.expiryDate)
//        assertEquals(updatedPass.accessLevel, result.accessLevel)
//    }
//}
