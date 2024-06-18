package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.AccessLog
import com.example.passidapp.models.Admin
import com.example.passidapp.models.Pass
import com.example.passidapp.models.PassRequest
import com.example.passidapp.models.User
import com.example.passidapp.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> get() = _userState

    private val _usersStates = MutableStateFlow<List<User>>(emptyList())
    val usersState: StateFlow<List<User>> get()  =  _usersStates

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    private val userRepository = UserRepository()
    fun getUser(user_id: String) {
        viewModelScope.launch {
            try {
                _userState.value = userRepository.getUser(user_id)
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                _usersStates.value = userRepository.getUsers()
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun createUser(user_id: String, first_name: String, last_name: String, middle_name: String?, birth_date: String, email_or_phone: String, password: String, admins: List<Admin> = listOf(), passes: List<Pass> = listOf(), access_logs: List<AccessLog> = listOf(), pass_requests: List<PassRequest> = listOf()) {
        viewModelScope.launch {
            try {
                val newUser = User(
                    user_id = user_id,
                    first_name = first_name,
                    last_name = last_name,
                    middle_name = middle_name,
                    birth_date = birth_date,
                    email_or_phone = email_or_phone,
                    password = password,
                    admins = admins,
                    passes = passes,
                    access_logs = access_logs,
                    pass_requests = pass_requests
                )
                _userState.value = userRepository.createUser(newUser)
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun deleteUser(user_id: String) {
        viewModelScope.launch {
            try {
                userRepository.deleteUser(user_id)
                _userState.value = null
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun updateUser(user_id: String, user: User)  {
        viewModelScope.launch {
            try {
                _userState.value = userRepository.updateUser(user_id, user)
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}
