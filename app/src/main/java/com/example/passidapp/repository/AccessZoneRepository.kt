package com.example.passidapp.repository

import com.example.passidapp.models.AccessZone
import com.example.passidapp.network.ApiService
import retrofit2.Response
import java.util.UUID


class AccessZoneRepository(private val apiService: ApiService) {

    suspend fun getAccessZone(zoneId: UUID): AccessZone {
        return apiService.getAccessZone(zoneId)
    }

    suspend fun createAccessZone(accessZone: AccessZone): AccessZone {
        return apiService.createAccessZone(accessZone)
    }

    suspend fun deleteAccessZone(zoneId: UUID): Response<Void> {
        return apiService.deleteAccessZone(zoneId)
    }

    suspend fun updateAccessZone(zoneId: UUID, accessZone: AccessZone): AccessZone {
        return apiService.updateAccessZone(zoneId, accessZone)
    }
}
