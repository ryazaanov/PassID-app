package com.example.passidapp.models

import java.util.UUID

data class Admin(
    val adminId: UUID?,
    val userId: UUID,
    val position: String,
    val role: String
)
