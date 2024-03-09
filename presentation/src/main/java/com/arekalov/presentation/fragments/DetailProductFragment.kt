package com.arekalov.presentation.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.arekalov.data.models.Product
import com.arekalov.presentation.R
import com.arekalov.presentation.databinding.FragmentDetailProductBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch


class DetailProductFragment : Fragment() {
    private lateinit var binding: FragmentDetailProductBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProduct()
    }

    private fun setProduct() {
        val product = arguments?.getParcelable<Product>("product")
        if (product != null) {
            binding.tvTitle.text = product.title
            binding.tvBrand.text = resources.getString(R.string.brand, product.brand)
            binding.tvPrice.text = resources.getString(R.string.price, product.price)
            binding.tvDescription.text = product.description
            lifecycleScope.launch {
                Glide.with(binding.root).load(product.thumbnail).into(binding.ivPhoto)
            }
        }
        else {
            Log.e("Error", "setProduct null product")
        }
    }
}