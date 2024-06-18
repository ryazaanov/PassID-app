package com.example.passidapp.repository

import com.example.passidapp.models.AccessLog
import com.example.passidapp.network.ApiService
import java.util.UUID

class AccessLogRepository(private val apiService: ApiService) {

    suspend fun getAccessLog(logId: UUID): AccessLog {
        return apiService.getAccessLog(logId)
    }

    suspend fun createAccessLog(accessLog: AccessLog): AccessLog {
        return apiService.createAccessLog(accessLog)
    }

}
