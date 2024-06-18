package com.example.passidapp.models

import java.util.UUID

data class AccessZone(
    val zoneId: UUID?,
    val zoneName: String,
    val description: String?
)
