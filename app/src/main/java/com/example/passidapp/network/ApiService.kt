package com.example.passidapp.network

import com.example.passidapp.models.AccessLog
import com.example.passidapp.models.AccessZone
import com.example.passidapp.models.Admin
import com.example.passidapp.models.Pass
import com.example.passidapp.models.PassRequest
import com.example.passidapp.models.User
import retrofit2.Response
import retrofit2.http.*
import java.util.UUID

interface ApiService {

    @GET("users/{user_id}")
    suspend fun getUser(@Path("user_id") userId: String): User

    @GET("users")
    suspend fun getUsers(): List<User>

    @POST("users/")
    suspend fun createUser(@Body user: User): User

    @DELETE("users/{user_id}")
    suspend fun deleteUser(@Path("user_id") userId: String): Response<Void>

    @PUT("users/{user_id}")
    suspend fun updateUser(@Path("user_id") userId: String, @Body user: User): User

    @GET("admins/{admin_id}")
    suspend fun getAdmin(@Path("admin_id") adminId: UUID): Admin

    @POST("admins/")
    suspend fun createAdmin(@Body admin: Admin): Admin

    @DELETE("admins/{admin_id}")
    suspend fun deleteAdmin(@Path("admin_id") adminId: UUID): Response<Void>

    @PUT("admins/{admin_id}")
    suspend fun updateAdmin(@Path("admin_id") adminId: UUID, @Body admin: Admin): Admin

    @GET("passes/{pass_id}")
    suspend fun getPass(@Path("pass_id") pass_id: String): Pass

    @GET("passes")
    suspend fun getPasses(): List<Pass>

    @POST("passes/")
    suspend fun createPass(@Body pass: Pass): Pass

    @DELETE("passes/{pass_id}")
    suspend fun deletePass(@Path("pass_id") pass_id: String): Response<Void>

    @PUT("passes/{pass_id}")
    suspend fun updatePass(@Path("pass_id") pass_id: String, @Body pass: Pass): Pass

    @GET("access_zones/{zone_id}")
    suspend fun getAccessZone(@Path("zone_id") zoneId: UUID): AccessZone

    @POST("access_zones/")
    suspend fun createAccessZone(@Body accessZone: AccessZone): AccessZone

    @DELETE("access_zones/{zone_id}")
    suspend fun deleteAccessZone(@Path("zone_id") zoneId: UUID): Response<Void>

    @PUT("access_zones/{zone_id}")
    suspend fun updateAccessZone(@Path("zone_id") zoneId: UUID, @Body accessZone: AccessZone): AccessZone

    @GET("access_logs/{log_id}")
    suspend fun getAccessLog(@Path("log_id") logId: UUID): AccessLog

    @POST("access_logs/")
    suspend fun createAccessLog(@Body accessLog: AccessLog): AccessLog

    @POST("pass_requests/")
    suspend fun createPassRequest(@Body passRequest: PassRequest): PassRequest

    @GET("pass_requests/{request_id}")
    suspend fun getPassRequest(@Path("request_id") requestId: UUID): PassRequest

    @PUT("pass_requests/{request_id}/approve")
    suspend fun approvePassRequest(
        @Path("request_id") requestId: UUID,
        @Query("admin_id") adminId: UUID,
        @Query("zone_id") zoneId: UUID,
        @Query("pass_type") passType: String,
        @Query("access_level") accessLevel: Int
    ): PassRequest

    @PUT("pass_requests/{request_id}/reject")
    suspend fun rejectPassRequest(@Path("request_id") requestId: UUID, @Query("admin_id") adminId: UUID): PassRequest
}
