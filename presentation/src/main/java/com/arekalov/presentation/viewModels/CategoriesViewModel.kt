package com.arekalov.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.data.network.ProductsRepository
import kotlinx.coroutines.launch

class CategoriesViewModel(private val repository: ProductsRepository): ViewModel() {
    private val categoriesLiveData = MutableLiveData<List<String>>()
    init {
        getCategories()
    }
    private fun getCategories(){
        viewModelScope.launch {
            try {
                val result = repository.getCategories()
                if (result != null) {
                    categoriesLiveData.value = result!!
                }
            } catch (ex: Exception) {}
        }
    }

    fun observeSearchedViewModel(): LiveData<List<String>> = categoriesLiveData
}