package com.arekalov.data.network

import com.arekalov.data.models.Categories
import com.arekalov.data.models.Product
import com.arekalov.data.models.Products
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class ProductsNetworkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val productsService = retrofit.create(ProductsApi::class.java)

    suspend fun getProduct(id: Int): Response<Product> {
        return productsService.getProduct(id.toString())
    }

    suspend fun getFirstProducts(): Response<Products> {
        return productsService.getFirstProducts()
    }

    suspend fun getProducts(skip: Int, limit: Int): Response<Products> {
        return productsService.getProducts(skip.toString(), limit.toString())
    }

    suspend fun searchProducts(keyword: String): Response<Products> {
        return productsService.searchProducts(keyword)
    }

    suspend fun getCategories(): Response<Categories> {
        return productsService.getCategories()
    }

    suspend fun getProductsByCategory(title: String): Response<Products> {
        return productsService.getProductsInCategory(title)
    }


}

interface ProductsApi {
    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: String): Response<Product>

    @GET("products")
    suspend fun getFirstProducts(): Response<Products>

    @GET("/products?")
    suspend fun getProducts(@Query("skip") skip: String, @Query("limit") limit: String): Response<Products>

    @GET("products/search?")
    suspend fun searchProducts(@Query("q") keyword: String): Response<Products>

    @GET("products/categories")
    suspend fun getCategories(): Response<Categories>

    @GET("products/category/{title}")
    suspend fun getProductsInCategory(@Path("title") title: String): Response<Products>
}