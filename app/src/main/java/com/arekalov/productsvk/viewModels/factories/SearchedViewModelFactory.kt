package com.arekalov.productsvk.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arekalov.data.network.ProductsRepository
import com.arekalov.productsvk.viewModels.SearchedViewModel
import javax.inject.Inject

class SearchedViewModelFactory @Inject constructor(
    private val repository: ProductsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchedViewModel(repository) as T
    }
}