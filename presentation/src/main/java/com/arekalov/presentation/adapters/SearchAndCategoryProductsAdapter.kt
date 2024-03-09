package com.arekalov.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.arekalov.data.models.Product
import com.arekalov.presentation.databinding.ProductCardBinding
import com.bumptech.glide.Glide

class SearchAndCategoryProductsAdapter :
    RecyclerView.Adapter<SearchAndCategoryProductsAdapter.VIewHolder>() {
    inner class VIewHolder(val binding: ProductCardBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, diffUtil)
    var onCLick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIewHolder {
        return VIewHolder(
            ProductCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: VIewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.binding.tvDescriptionProduct.text = product.description
        holder.binding.tvTitleProduct.text = product.title
        Glide.with(holder.itemView)
            .load(product.thumbnail)
            .into(holder.binding.ivPhoto)
        holder.itemView.setOnClickListener {
            onCLick!!.invoke(differ.currentList[position])
        }
    }

}