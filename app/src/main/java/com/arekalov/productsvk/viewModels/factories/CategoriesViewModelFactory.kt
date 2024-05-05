package com.arekalov.presentation.viewModels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.viewModels.CategoriesViewModel

class CategoriesViewModelFactory(
    private val repository: ProductsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoriesViewModel(repository) as T
    }
}