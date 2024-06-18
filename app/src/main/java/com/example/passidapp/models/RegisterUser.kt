package com.example.passidapp.models

data class UserLogin(
    val user_id: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String?,
    val birth_date: String,
    val email_or_phone: String,
    val password: String
)
