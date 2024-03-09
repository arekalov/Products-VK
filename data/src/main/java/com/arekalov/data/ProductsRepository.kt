package com.arekalov.data

import com.arekalov.data.ProductsNetworkService

class ProductsRepository(
    private val networkService: ProductsNetworkService
) {
    suspend fun getProducts() {
        return networkService.getProducts()
    }

    suspend fun getProduct(id: String) {
        return networkService.getProduct(id)
    }
}