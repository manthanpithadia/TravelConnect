package com.example.travelconnect.data.model

sealed class ExtendedViewState {
    object Loading : ExtendedViewState()
    data class Success(val locationDetails: ExtendedModel) : ExtendedViewState()
    data class Error(val errorMessage: String) : ExtendedViewState()
}
