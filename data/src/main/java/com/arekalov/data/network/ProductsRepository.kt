package com.arekalov.data.network


import com.arekalov.data.models.Product
import com.arekalov.data.pagination.ProductsPagingSource

class ProductsRepository(
    private val networkService: ProductsNetworkService
) {
    suspend fun getFirstProducts(): List<Product>? {
        val result = networkService.getFirstProducts()
        if (result.isSuccessful) {
            return networkService.getFirstProducts().body()?.products
        }
        return null
    }

    suspend fun getProduct(id: Int): Product? {
        val result = networkService.getProduct(id)
        if (result.isSuccessful) {
            return networkService.getProduct(id).body()
        }
        return null
    }

    suspend fun getProducts(skip: Int, limit: Int): List<Product>? {
        val result = networkService.getProducts(skip, limit)
        if (result.isSuccessful) {
            return networkService.getProducts(skip, limit).body()!!.products
        }
        return null
    }

    fun productsPagingSource() = ProductsPagingSource()
}