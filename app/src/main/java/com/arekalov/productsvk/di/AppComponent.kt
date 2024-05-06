package com.arekalov.productsvk.di

import com.arekalov.productsvk.adapters.MyLoadStateAdapter
import com.arekalov.data.network.ProductsApi
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.productsvk.MainActivity
import com.arekalov.productsvk.adapters.*
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [NetworkModule::class, AdaptersModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}


@Module
object AdaptersModule{
    @Provides
    fun provideSearchAndCategoryProductsAdapter(): SearchAndCategoryProductsAdapter {
        return SearchAndCategoryProductsAdapter()
    }

    @Provides
    fun provideCategoriesAdapter(): CategoriesAdapter {
        return CategoriesAdapter()
    }

    @Provides
    fun provideProductsAdapter(): ProductsAdapter {
        return ProductsAdapter()
    }

    @Provides
    fun provideMyLoadStateAdapter(): MyLoadStateAdapter {
        return MyLoadStateAdapter()
    }
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
