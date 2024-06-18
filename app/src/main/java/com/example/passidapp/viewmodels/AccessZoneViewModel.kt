package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.AccessZone
import com.example.passidapp.repository.AccessZoneRepository
import kotlinx.coroutines.launch
import java.util.UUID

class AccessZoneViewModel(private val accessZoneRepository: AccessZoneRepository) : ViewModel() {

    fun getAccessZone(zoneId: UUID, onResult: (AccessZone) -> Unit) {
        viewModelScope.launch {
            val accessZone = accessZoneRepository.getAccessZone(zoneId)
            onResult(accessZone)
        }
    }

    fun createAccessZone(accessZone: AccessZone, onResult: (AccessZone) -> Unit) {
        viewModelScope.launch {
            val newAccessZone = accessZoneRepository.createAccessZone(accessZone)
            onResult(newAccessZone)
        }
    }

    fun deleteAccessZone(zoneId: UUID, onResult: () -> Unit) {
        viewModelScope.launch {
            accessZoneRepository.deleteAccessZone(zoneId)
            onResult()
        }
    }

    fun updateAccessZone(zoneId: UUID, accessZone: AccessZone, onResult: (AccessZone) -> Unit) {
        viewModelScope.launch {
            val updatedAccessZone = accessZoneRepository.updateAccessZone(zoneId, accessZone)
            onResult(updatedAccessZone)
        }
    }
}
