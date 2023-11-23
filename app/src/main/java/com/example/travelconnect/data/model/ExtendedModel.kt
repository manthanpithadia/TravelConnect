package com.example.travelconnect.data.model

data class ExtendedModel(
    val name: String,
    val imgs: List<String>,
    val lat: String,
    val long: String,
    val review: List<Reviews>,
    val desc: String
)

data class Reviews(
    val uname: String,
    val desc: String
)
