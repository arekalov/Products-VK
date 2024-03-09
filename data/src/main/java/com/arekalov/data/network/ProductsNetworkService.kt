package com.arekalov.data.network

import com.arekalov.data.models.Product
import com.arekalov.data.models.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class ProductsNetworkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productsService = retrofit.create(ProductsApi::class.java)

    suspend fun getProduct(id: String): Response<Product> {
        return productsService.getProduct(id)
    }

    suspend fun getProducts(): Response<Products> {
        return productsService.getProducts()
    }

}

interface ProductsApi {
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<Product>

    @GET("products")
    suspend fun getProducts(): Response<Products>
}