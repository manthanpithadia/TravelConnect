package com.example.travelconnect.data.model

data class ExtendedModel(
    val name: String,
    val img: List<String>,
    val lat: String,
    val long: String,
    val desc: String,
    val reviews: List<Reviews>
)

data class Reviews(
    val name: String,
    val description: String,
    val _id: String
)
