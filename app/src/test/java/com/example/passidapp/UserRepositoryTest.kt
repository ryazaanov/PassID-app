//package com.example.passidapp.repository
//
//import com.example.passidapp.models.User
//import com.example.passidapp.network.ApiService
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
//class UserRepositoryTest {
//
//    private lateinit var mockWebServer: MockWebServer
//    private lateinit var apiService: ApiService
//    private lateinit var userRepository: UserRepository
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
//        userRepository = UserRepository(apiService)
//    }
//
//    @After
//    fun teardown() {
//        mockWebServer.shutdown()
//    }
//
//    @Test
//    fun testGetUser() = runBlocking {
//        val userId = UUID.randomUUID()
//        val user = User(
//            userId = userId,
//            firstName = "John",
//            lastName = "Doe",
//            middleName = "M",
//            birthDate = SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"),
//            admins = listOf(),
//            passes = listOf(),
//            accessLogs = listOf(),
//            passRequests = listOf()
//        )
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setBody(gson.toJson(user))
//                .addHeader("Content-Type", "application/json")
//        )
//
//        val result = userRepository.getUser(userId)
//        assertEquals(user, result)
//    }
//
//    @Test
//    fun testCreateUser() = runBlocking {
//        val user = User(
//            userId = null,
//            firstName = "John",
//            lastName = "Doe",
//            middleName = "M",
//            birthDate = SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"),
//            admins = listOf(),
//            passes = listOf(),
//            accessLogs = listOf(),
//            passRequests = listOf()
//        )
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setBody(gson.toJson(user))
//                .addHeader("Content-Type", "application/json")
//        )
//
//        val result = userRepository.createUser(user)
//        assertEquals(user.firstName, result.firstName)
//        assertEquals(user.lastName, result.lastName)
//        assertEquals(user.middleName, result.middleName)
//        assertEquals(user.birthDate, result.birthDate)
//    }
//
//    @Test
//    fun testDeleteUser() = runBlocking {
//        val userId = UUID.randomUUID()
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setResponseCode(204) // No Content
//        )
//
//        val response = userRepository.deleteUser(userId)
//        assertEquals(true, response.isSuccessful)
//    }
//
//    @Test
//    fun testUpdateUser() = runBlocking {
//        val userId = UUID.randomUUID()
//        val updatedUser = User(
//            userId = userId,
//            firstName = "Jane",
//            lastName = "Doe",
//            middleName = "A",
//            birthDate = SimpleDateFormat("yyyy-MM-dd").parse("1992-02-02"),
//            admins = listOf(),
//            passes = listOf(),
//            accessLogs = listOf(),
//            passRequests = listOf()
//        )
//
//        mockWebServer.enqueue(
//            MockResponse()
//                .setBody(gson.toJson(updatedUser))
//                .addHeader("Content-Type", "application/json")
//        )
//
//        val result = userRepository.updateUser(userId, updatedUser)
//        assertEquals(updatedUser, result)
//    }
//}
