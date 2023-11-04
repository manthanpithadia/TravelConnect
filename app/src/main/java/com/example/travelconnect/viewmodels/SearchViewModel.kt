package com.example.travelconnect.viewmodels

import ImageApiService
import ImageModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {

    private val imageApiService = ImageApiService()
    private val imageListLiveData = MutableLiveData<List<ImageModel>>()
    private val errorLiveData = MutableLiveData<Unit>()

    fun getImageList(): LiveData<List<ImageModel>> {
        return imageListLiveData
    }

    fun getError(): LiveData<Unit> {
        return errorLiveData
    }

    fun fetchImages() {
        imageApiService.getImages(
            { imageList ->
                imageListLiveData.value = imageList
            },
            {
                errorLiveData.value = Unit
            }
        )
    }
}