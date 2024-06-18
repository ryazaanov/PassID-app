package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.AccessLog
import com.example.passidapp.repository.AccessLogRepository
import kotlinx.coroutines.launch
import java.util.UUID

class AccessLogViewModel(private val accessLogRepository: AccessLogRepository) : ViewModel() {

    fun getAccessLog(logId: UUID, onResult: (AccessLog) -> Unit) {
        viewModelScope.launch {
            val accessLog = accessLogRepository.getAccessLog(logId)
            onResult(accessLog)
        }
    }

    fun createAccessLog(accessLog: AccessLog, onResult: (AccessLog) -> Unit) {
        viewModelScope.launch {
            val newAccessLog = accessLogRepository.createAccessLog(accessLog)
            onResult(newAccessLog)
        }
    }
}
