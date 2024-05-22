package com.example.passidapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passidapp.models.PassCardModel
import com.example.passidapp.ui_state.PassCardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PassCardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PassCardUiState())
    val uiState: StateFlow<PassCardUiState> = _uiState

    init {
        loadPassCards()
    }

    private fun loadPassCards() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            // Имитация задержки для загрузки данных
            kotlinx.coroutines.delay(1000)
            try {
                // Заглушка с тестовыми данными
                val passCards = listOf(
                    PassCardModel(id = "1", passType = "Временный пропуск", companyName = "АО ГКНПЦ", qrCodeIdentifier = "12345"),
                    PassCardModel(id = "2", passType = "Постоянный пропуск", companyName = "ООО Рога и Копыта", qrCodeIdentifier = "67890")
                )
                _uiState.value = _uiState.value.copy(passCards = passCards, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}