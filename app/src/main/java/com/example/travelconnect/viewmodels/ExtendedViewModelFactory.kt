package com.example.travelconnect.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.data.repositories.Repository

@Suppress("UNREACHABLE_CODE")
class ExtendedViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExtendedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExtendedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
        return super.create(modelClass)
    }
}