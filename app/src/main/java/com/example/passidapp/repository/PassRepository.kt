package com.example.passidapp.repository

import com.example.passidapp.models.Pass
import com.example.passidapp.network.ApiService
import com.example.passidapp.network.RetrofitInstance
import retrofit2.Response

class PassRepository() {
    var apiService: ApiService = RetrofitInstance.api
    fun getApi(){

    }
    suspend fun getPass(pass_id: String): Pass {
        return apiService.getPass(pass_id)
    }

    suspend fun getPasses(): List<Pass> {
        return apiService.getPasses()
    }
    suspend fun createPass(pass: Pass): Pass {
        return apiService.createPass(pass)
    }

    suspend fun deletePass(pass_id: String): Response<Void> {
        return apiService.deletePass(pass_id)
    }

    suspend fun updatePass(pass_id: String, pass: Pass): Pass {
        return apiService.updatePass(pass_id, pass)
    }
}
