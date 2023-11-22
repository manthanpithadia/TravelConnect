package com.example.travelconnect.data.model

data class AddTripRequest(
    val token: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val foodPref: String
)
