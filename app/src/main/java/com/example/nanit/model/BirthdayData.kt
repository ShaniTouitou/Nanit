package com.example.nanit.model

/**
 * This represent the birthday data that were getting from the server as json.
 */
data class BirthdayData(
    val name: String,
    val dob: Long,
    val theme: String
)
