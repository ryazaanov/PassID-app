package com.example.passidapp.models

data class PassCardModel(
    val id: String,
    val passType: String,
    val companyName: String,
    val qrCodeIdentifier: String
)