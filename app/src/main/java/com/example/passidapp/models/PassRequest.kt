package com.example.passidapp.models

import java.util.Date
import java.util.UUID

data class PassRequest(
    val requestId: UUID?,
    val userId: UUID,
    val requestDate: Date,
    val requestStatus: String,
    val approved: Boolean,
    val adminId: UUID?
)
