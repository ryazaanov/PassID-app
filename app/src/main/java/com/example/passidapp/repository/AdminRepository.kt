package com.example.passidapp.repository

import com.example.passidapp.models.Admin
import com.example.passidapp.network.ApiService
import retrofit2.Response
import java.util.UUID

class AdminRepository(private val apiService: ApiService) {

    suspend fun getAdmin(adminId: UUID): Admin {
        return apiService.getAdmin(adminId)
    }

    suspend fun createAdmin(admin: Admin): Admin {
        return apiService.createAdmin(admin)
    }

    suspend fun deleteAdmin(adminId: UUID): Response<Void> {
        return apiService.deleteAdmin(adminId)
    }

    suspend fun updateAdmin(adminId: UUID, admin: Admin): Admin {
        return apiService.updateAdmin(adminId, admin)
    }
}
