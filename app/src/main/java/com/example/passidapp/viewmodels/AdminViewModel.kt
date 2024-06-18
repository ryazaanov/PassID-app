package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.Admin
import com.example.passidapp.repository.AdminRepository
import kotlinx.coroutines.launch
import java.util.UUID

class AdminViewModel(private val adminRepository: AdminRepository) : ViewModel() {

    fun getAdmin(adminId: UUID, onResult: (Admin) -> Unit) {
        viewModelScope.launch {
            val admin = adminRepository.getAdmin(adminId)
            onResult(admin)
        }
    }

    fun createAdmin(admin: Admin, onResult: (Admin) -> Unit) {
        viewModelScope.launch {
            val newAdmin = adminRepository.createAdmin(admin)
            onResult(newAdmin)
        }
    }

    fun deleteAdmin(adminId: UUID, onResult: () -> Unit) {
        viewModelScope.launch {
            adminRepository.deleteAdmin(adminId)
            onResult()
        }
    }

    fun updateAdmin(adminId: UUID, admin: Admin, onResult: (Admin) -> Unit) {
        viewModelScope.launch {
            val updatedAdmin = adminRepository.updateAdmin(adminId, admin)
            onResult(updatedAdmin)
        }
    }
}
