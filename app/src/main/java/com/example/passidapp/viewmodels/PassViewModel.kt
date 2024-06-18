package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.Pass
import com.example.passidapp.repository.PassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PassViewModel() : ViewModel() {

//    private val _passState = MutableStateFlow<List<Pass>>(emptyList())
//    val passState: StateFlow<List<Pass>> get() = _passState
//
//    private val _errorState = MutableStateFlow<String?>(null)
//    val errorState: StateFlow<String?> get() = _errorState

    private val _passState = MutableStateFlow<Pass?>(null)
    val passState: StateFlow<Pass?> get() = _passState

    private val _passesState = MutableStateFlow<List<Pass>>(emptyList())
    val passesState: StateFlow<List<Pass>> get() = _passesState

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    //private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    private val passRepository = PassRepository()
    fun getPass(pass_id: String) {
        viewModelScope.launch {
            try {
                _passState.value = passRepository.getPass(pass_id)
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun getPasses() {
        viewModelScope.launch {
            try {
                _passesState.value = passRepository.getPasses()
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun createPass(pass_id: String, user_id: String, zone_id: String, pass_name: String, pass_type: String, issue_date: String, expiry_date: String, access_level: Int) {
        viewModelScope.launch {
            try {
//                val formattedIssueDate = formatDate(issue_date)
//                val formattedExpiryDate = formatDate(issue_date)
                val newPass = Pass(
                    pass_id = pass_id,
                    user_id = user_id,
                    zone_id = zone_id,
                    pass_name  = pass_name,
                    pass_type = pass_type,
                    issue_date = issue_date,
                    expiry_date = issue_date,
                    access_level = access_level
                )
                _passState.value = passRepository.createPass(newPass)
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun deletePass(pass_id: String) {
        viewModelScope.launch {
            try {
                passRepository.deletePass(pass_id)
                _passState.value = null
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }

    fun updatePass(pass_id: String, pass: Pass) {
        viewModelScope.launch {
            try {
                _passState.value = passRepository.updatePass(pass_id, pass)
                _errorState.value = null
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}