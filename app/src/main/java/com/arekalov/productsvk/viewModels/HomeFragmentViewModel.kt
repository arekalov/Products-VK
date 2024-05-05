package com.arekalov.productsvk.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.arekalov.data.models.Product
import com.arekalov.data.network.ProductsRepository

private const val ITEMS_PER_PAGE = 20
class HomeFragmentViewModel(val repository: ProductsRepository): ViewModel() {
    private var productsLiveData = MutableLiveData<PagingData<Product>>()
    val response =  Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = {repository.productsPagingSource()}
    ).liveData
        .cachedIn(viewModelScope)

    fun getProducts(): LiveData<PagingData<Product>>? {
        try {
            productsLiveData.value = response.value
            return response
        } catch (ex: Exception) {
            Log.e("error", "getProducts: response is null", )
            return null}
    }
}