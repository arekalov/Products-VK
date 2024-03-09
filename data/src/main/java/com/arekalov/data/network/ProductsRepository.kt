package com.arekalov.data.network


import com.arekalov.data.models.Categories
import com.arekalov.data.models.Product
import com.arekalov.data.pagination.ProductsPagingSource

class ProductsRepository(
    private val networkService: ProductsNetworkService
) {
    suspend fun getFirstProducts(): List<Product>? {
        val result = networkService.getFirstProducts()
        if (result.isSuccessful) {
            return result.body()?.products
        }
        return null
    }

    suspend fun getProduct(id: Int): Product? {
        val result = networkService.getProduct(id)
        if (result.isSuccessful) {
            return result.body()
        }
        return null
    }

    suspend fun getProducts(skip: Int, limit: Int): List<Product>? {
        val result = networkService.getProducts(skip, limit)
        if (result.isSuccessful) {
            return result.body()!!.products
        }
        return null
    }

    suspend fun searchProducts(keyword: String): List<Product>? {
        val result = networkService.searchProducts(keyword)
        if (result.isSuccessful) {
            return result.body()!!.products
        }
        return null
    }

    suspend fun getCategories(): Categories? {
        val result = networkService.getCategories()
        if (result.isSuccessful) {
            return result.body()
        }
        return null
    }

    suspend fun getProductsByCategory(title: String): List<Product>? {
        val result = networkService.getProductsByCategory(title)
        if (result.isSuccessful) {
            return result.body()!!.products
        }
        return null
    }

    fun productsPagingSource() = ProductsPagingSource()
}