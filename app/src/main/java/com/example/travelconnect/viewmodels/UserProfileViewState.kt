package com.example.travelconnect.viewmodels

import androidx.lifecycle.LiveData
import com.example.travelconnect.data.model.UserProfile

sealed class UserProfileViewState {
    object Loading : UserProfileViewState()
    data class Success(val userDetails: UserProfile) : UserProfileViewState()
    data class Error(val errorMessage: String) : UserProfileViewState()
}