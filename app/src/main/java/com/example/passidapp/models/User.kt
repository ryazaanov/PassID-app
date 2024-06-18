package com.example.passidapp.models

data class User(
    val user_id: String,
    val first_name: String,
    val last_name: String,
    val middle_name: String?,
    val birth_date: String,
    val email_or_phone: String,
    val password: String,
    val admins: List<Admin> = listOf(),
    val passes: List<Pass> = listOf(),
    val access_logs: List<AccessLog> = listOf(),
    val pass_requests: List<PassRequest> = listOf()
)
