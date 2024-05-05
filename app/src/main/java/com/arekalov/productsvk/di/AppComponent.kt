package com.arekalov.productsvk.di

import com.arekalov.data.models.Categories
import com.arekalov.data.models.Product
import com.arekalov.data.models.Products
import com.arekalov.data.network.ProductsApi
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.productsvk.MainActivity
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}

@Module
interface NetworkModule {
    companion object {
        @Provides
        fun provideNetworkRepository(networkService: ProductsNetworkService): ProductsRepository {
            return ProductsRepository(networkService)
        }

        @Provides
        fun provideNetworkService(productsApi: ProductsApi): ProductsNetworkService {
            return ProductsNetworkService()
        }

        @Provides
        fun provideProductsApi(): ProductsApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ProductsApi::class.java)
        }
    }

}
