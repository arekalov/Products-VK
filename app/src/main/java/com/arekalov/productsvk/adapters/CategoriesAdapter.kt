package com.arekalov.productsvk.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arekalov.productsvk.databinding.CategoryCardBinding


class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.VIewHolder>() {
    inner class VIewHolder(val binding: CategoryCardBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, diffUtil)
    var onCLick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIewHolder {
        return VIewHolder(
            CategoryCardBinding.inflate(
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
        val category = differ.currentList[position]
        holder.binding.tvCategory.text = category.capitalize()
        holder.itemView.setOnClickListener {
            onCLick!!.invoke(differ.currentList[position])
        }
    }

}