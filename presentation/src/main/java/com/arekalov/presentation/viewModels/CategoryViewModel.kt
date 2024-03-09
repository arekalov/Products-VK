package com.arekalov.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.data.network.ProductsRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: ProductsRepository): ViewModel() {
    private val categoriesLiveData = MutableLiveData<List<String>>()
    fun getCategories(){
        viewModelScope.launch {
            val result = repository.getCategories()
            if (result != null) {
                categoriesLiveData.value = result!!
            }
        }
    }

    fun observeSearchedViewModel(): LiveData<List<String>> = categoriesLiveData
}