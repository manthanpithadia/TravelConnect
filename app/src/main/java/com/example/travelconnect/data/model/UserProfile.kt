package com.example.travelconnect.data.model

data class UserProfile(
    val user: User
)

data class User(
    val _id: String,
    val email: String,
    val name: String,
    val password: String,
    val token: String,
    val avatar: String,
    val trips: List<Trip>,
    val __v: Int
)

data class Trip(
    val location: String,
    val startDate: String,
    val endDate: String,
    val foodPref: String,
    val _id: String
)
