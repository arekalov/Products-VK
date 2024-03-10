package com.arekalov.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arekalov.data.models.Product
import com.arekalov.data.models.Products
import com.arekalov.data.network.ProductsNetworkService
import kotlinx.coroutines.delay
import kotlin.math.max

class ProductsPagingSource : PagingSource<Int, Product>() {
    private val STARTING_KEY = 0
    val network = ProductsNetworkService()

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val product = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = product.id - (state.config.pageSize / 2))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val start = params.key ?: STARTING_KEY
        try {
            return LoadResult.Page(
                data = network.getProducts(start, params.loadSize).body()!!.products,
                prevKey = when (start) {
                    STARTING_KEY -> null
                    else -> ensureValidKey(key = start - params.loadSize)
                },
                nextKey = start + params.loadSize + 1
            )
        } catch (ex: Exception) {return LoadResult.Error(ex)}
    }
}