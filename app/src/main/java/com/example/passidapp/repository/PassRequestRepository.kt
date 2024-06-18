package com.example.passidapp.repository

import com.example.passidapp.models.PassRequest
import com.example.passidapp.network.ApiService
import java.util.UUID

class PassRequestRepository(private val apiService: ApiService) {

    suspend fun getPassRequest(requestId: UUID): PassRequest {
        return apiService.getPassRequest(requestId)
    }

    suspend fun createPassRequest(passRequest: PassRequest): PassRequest {
        return apiService.createPassRequest(passRequest)
    }

    suspend fun approvePassRequest(
        requestId: UUID,
        adminId: UUID,
        zoneId: UUID,
        passType: String,
        accessLevel: Int
    ): PassRequest {
        return apiService.approvePassRequest(requestId, adminId, zoneId, passType, accessLevel)
    }

    suspend fun rejectPassRequest(requestId: UUID, adminId: UUID): PassRequest {
        return apiService.rejectPassRequest(requestId, adminId)
    }
}
