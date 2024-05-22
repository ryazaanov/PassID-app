package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.PassCreateModel
import com.example.passidapp.ui_state.PassCreateUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreatePassViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PassCreateUiState())
    val uiState: StateFlow<PassCreateUiState> = _uiState

    fun createPass(passCreateModel: PassCreateModel) {
        viewModelScope.launch {
            _uiState.value = PassCreateUiState(isLoading = true)
            try {
                // Имитация задержки для асинхронного вызова
                delay(2000)

                // Имитация успешного создания пропуска
                // Здесь можно добавить логику проверки или обработки данных
                // Пока что просто возвращаем успешный результат

                _uiState.value = PassCreateUiState()
            } catch (e: Exception) {
                _uiState.value = PassCreateUiState(error = e.message)
            }
        }
    }
}
