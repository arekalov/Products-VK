package com.arekalov.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arekalov.data.models.Product

class SearchedViewModel : ViewModel() {
    val searchedViewModel = MutableLiveData<List<Product>>()
    suspend fun searchProducts(keyword: String){

    }
}