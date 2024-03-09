package com.arekalov.presentation.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arekalov.data.models.Product
import com.arekalov.data.network.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentViewModel(val repository: ProductsRepository): ViewModel() {
    private val productsLiveData = MutableLiveData<List<Product>>()
    fun getProducts() {
        viewModelScope.launch {
            val result = repository.getProducts()
            if (result != null) {
                productsLiveData.value = result!!
            }
            else {
                Log.e(this.javaClass.toString(), "null result")
            }
        }
    }

    fun observeProductsLiveData(): LiveData<List<Product>> = productsLiveData
}