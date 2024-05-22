package com.example.passidapp.ui_state

import com.example.passidapp.models.PassCardModel

data class PassCardUiState(
    val isLoading: Boolean = false,
    val passCards: List<PassCardModel> = emptyList(),
    val error: String? = null
)
