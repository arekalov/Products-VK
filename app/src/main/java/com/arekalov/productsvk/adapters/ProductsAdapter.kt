package com.arekalov.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.arekalov.data.models.Product
import com.arekalov.productsvk.databinding.ProductCardBinding

class ProductsAdapter : PagingDataAdapter<Product, ProductViewBinding>(PRODUCT_DIFF_CALLBACK) {
    var onClick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewBinding {
        return ProductViewBinding(
            ProductCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewBinding, position: Int) {
        val product = getItem(position)
        if (product != null) {
            holder.bind(product)
        }
        holder.itemView.setOnClickListener{
            onClick!!.invoke(getItem(position)!!)
        }

    }

    companion object {
        private val PRODUCT_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}