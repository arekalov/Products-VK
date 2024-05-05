package com.arekalov.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.arekalov.data.models.Product
import com.arekalov.productsvk.databinding.ProductCardBinding
import com.bumptech.glide.Glide

class ProductViewBinding(
    private val binding: ProductCardBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(product: Product) {
        binding.apply {
            binding.tvTitleProduct.text = product.title
            binding.tvDescriptionProduct.text = product.description
            Glide.with(itemView)
                .load(product.thumbnail)
                .into(binding.ivPhoto)
        }
    }
}