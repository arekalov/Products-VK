package com.arekalov.presentation.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.arekalov.presentation.R
import com.arekalov.presentation.databinding.CarouselItemBinding
import com.bumptech.glide.Glide


class CarouselAdapter(private var arrayList: List<String>) :
    RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CarouselItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(arrayList[position])
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(val binding: CarouselItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var imageView: ImageView
        init {
            imageView = binding.ivPhoto
        }
    }
}