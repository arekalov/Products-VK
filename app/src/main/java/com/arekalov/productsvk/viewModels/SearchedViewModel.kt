package com.arekalov.productsvk.viewModels

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
        try {
            viewModelScope.launch {
                val result = repository.searchProducts(keyword)
                if (result != null) {
                    searchedViewModel.value = result!!
                }
            }
        } catch (ex: Exception) {}
     }

    fun getProductByCategory(category: String){
        viewModelScope.launch {
            try {
                val result = repository.getProductsByCategory(category)
                if (result != null) {
                    searchedViewModel.value = result!!
                }
            } catch (ex: Exception) {}
        }
    }

    fun observeSearchedViewModel(): LiveData<List<Product>> = searchedViewModel
}