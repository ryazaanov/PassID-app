package com.example.passidapp.models

data class Pass(
    val pass_id: String,
    val user_id: String,
    val zone_id: String,
    val pass_name: String,
    val pass_type: String,
    val issue_date: String,
    val expiry_date: String,
    val access_level: Int
)
