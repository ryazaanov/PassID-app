package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.PassRequest
import com.example.passidapp.repository.PassRequestRepository
import kotlinx.coroutines.launch
import java.util.UUID

class PassRequestViewModel(private val passRequestRepository: PassRequestRepository) : ViewModel() {

    fun getPassRequest(requestId: UUID, onResult: (PassRequest) -> Unit) {
        viewModelScope.launch {
            val passRequest = passRequestRepository.getPassRequest(requestId)
            onResult(passRequest)
        }
    }

    fun createPassRequest(passRequest: PassRequest, onResult: (PassRequest) -> Unit) {
        viewModelScope.launch {
            val newPassRequest = passRequestRepository.createPassRequest(passRequest)
            onResult(newPassRequest)
        }
    }

    fun approvePassRequest(
        requestId: UUID,
        adminId: UUID,
        zoneId: UUID,
        passType: String,
        accessLevel: Int,
        onResult: (PassRequest) -> Unit
    ) {
        viewModelScope.launch {
            val approvedPassRequest = passRequestRepository.approvePassRequest(requestId, adminId, zoneId, passType, accessLevel)
            onResult(approvedPassRequest)
        }
    }

    fun rejectPassRequest(requestId: UUID, adminId: UUID, onResult: (PassRequest) -> Unit) {
        viewModelScope.launch {
            val rejectedPassRequest = passRequestRepository.rejectPassRequest(requestId, adminId)
            onResult(rejectedPassRequest)
        }
    }
}
