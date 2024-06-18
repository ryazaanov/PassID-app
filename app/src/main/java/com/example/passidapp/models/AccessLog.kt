package com.example.passidapp.models

import java.util.Date
import java.util.UUID

data class AccessLog(
    val logId: UUID?,
    val userId: UUID,
    val passId: UUID,
    val accessDatetime: Date,
    val accessType: String
)
