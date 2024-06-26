package com.arekalov.productsvk.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arekalov.data.models.Product
import com.arekalov.productsvk.adapters.CarouselAdapter
import com.arekalov.productsvk.R
import com.arekalov.productsvk.databinding.FragmentDetailProductBinding
import com.google.android.material.carousel.CarouselLayoutManager


class DetailProductFragment : Fragment() {
    private lateinit var binding: FragmentDetailProductBinding
    private lateinit var carouselAdapter: CarouselAdapter


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

    private fun setCarouselAdapter(images: List<String>) {

        carouselAdapter = CarouselAdapter(images)
        binding.rvCarousel.adapter = carouselAdapter
        binding.rvCarousel.layoutManager = CarouselLayoutManager()
    }

    private fun setProduct() {
        val product = arguments?.getParcelable<Product>("product")
        if (product != null) {
            setCarouselAdapter(product.images)
            binding.tvTitle.text = product.title
            binding.tvBrand.text = resources.getString(R.string.brand, product.brand)
            binding.tvPrice.text = resources.getString(R.string.price, product.price)
            binding.tvDescription.text = product.description

        }
        else {
            Log.e("Error", "setProduct null product")
        }
    }
}