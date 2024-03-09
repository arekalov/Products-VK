package com.arekalov.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.data.models.Product
import com.arekalov.data.network.ProductsRepository
import kotlinx.coroutines.launch

class SearchedViewModel(val repository: ProductsRepository) : ViewModel() {
    private val searchedViewModel = MutableLiveData<List<Product>>()
    suspend fun searchProducts(keyword: String){
        viewModelScope.launch {
            val result = repository.searchProducts(keyword)
            if (result != null) {
                searchedViewModel.value = result!!
            }
        }
    }

    fun observeSearchedViewModel(): LiveData<List<Product>> = searchedViewModel
}