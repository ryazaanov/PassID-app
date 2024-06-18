package com.example.passidapp.repository

import com.example.passidapp.models.User
import com.example.passidapp.network.ApiService
import com.example.passidapp.network.RetrofitInstance
import retrofit2.Response

class UserRepository() {
    var apiService: ApiService = RetrofitInstance.api
    suspend fun getUser(user_id: String): User {
        return apiService.getUser(user_id)
    }

    suspend fun getUsers(): List<User>  {
        return apiService.getUsers()
    }

    suspend fun createUser(user: User): User {
        return apiService.createUser(user)
    }

    suspend fun deleteUser(user_id: String): Response<Void> {
        return apiService.deleteUser(user_id)
    }

    suspend fun updateUser(user_id: String, user: User): User {
        return apiService.updateUser(user_id, user)
    }
}
