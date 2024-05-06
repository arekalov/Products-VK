package com.arekalov.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arekalov.data.models.Product
import com.arekalov.data.models.Products
import com.arekalov.data.network.ProductsNetworkService
import kotlinx.coroutines.delay
import kotlin.math.max

class ProductsPagingSource : PagingSource<Int, Product>() {
    private val STARTING_KEY = 0
    private val network = ProductsNetworkService()
    private val pageSize = 20

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val product = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = product.id - (state.config.pageSize))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val start = params.key ?: STARTING_KEY
        try {
            delay(2000)
            Log.e("lodading", start.toString(), )
            val loadSize = minOf(params.loadSize, pageSize)
            return if (start < 100) {
                LoadResult.Page(
                    data = network.getProducts(start, loadSize).body()!!.products,
                    prevKey = when (start) {
                        STARTING_KEY -> null
                        else -> ensureValidKey(key = start - loadSize)
                    },
                    nextKey = start + loadSize
                )
            } else {
                LoadResult.Error(Throwable("All items have been loaded"))
            }
        } catch (ex: Exception) {
            return LoadResult.Error(ex)
        }
    }

}