package com.arekalov.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arekalov.data.models.Product
import com.arekalov.presentation.databinding.ProductCardBinding
import com.bumptech.glide.Glide

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    inner class ProductsViewHolder(val binding: ProductCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    var productsList = ArrayList<Product>()
        set(value) {
            field = value;
            notifyDataSetChanged()
        }
    var onClick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ProductCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.binding.tvTitleProduct.text = productsList[position].title
        holder.binding.tvDescriptionProduct.text = productsList[position].description
        Glide.with(holder.itemView)
            .load(productsList[position].thumbnail)
            .into(holder.binding.ivPhoto)
    }
}