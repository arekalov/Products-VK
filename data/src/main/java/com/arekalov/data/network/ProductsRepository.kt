package com.arekalov.data.network

import com.arekalov.data.ProductsNetworkService
import com.arekalov.data.models.Product
import com.arekalov.data.models.Products
import retrofit2.Response

class ProductsRepository(
    private val networkService: ProductsNetworkService
) {
    suspend fun getProducts(): List<Product>? {
        val result = networkService.getProducts()
        if (result.isSuccessful) {
            return networkService.getProducts().body()?.products
        }
        return null
    }

    suspend fun getProduct(id: String): Product? {
        val result = networkService.getProduct(id)
        if (result.isSuccessful) {
            return networkService.getProduct(id).body()
        }
        return null
    }
}