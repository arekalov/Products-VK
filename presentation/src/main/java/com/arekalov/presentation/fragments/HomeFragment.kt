package com.arekalov.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.data.models.Product
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.adapters.ProductsAdapter
import com.arekalov.presentation.databinding.FragmentHomeBinding
import com.arekalov.presentation.viewModels.HomeFragmentViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var productsViewModel: HomeFragmentViewModel
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val productsRepository = ProductsRepository(ProductsNetworkService())
        productsViewModel = HomeFragmentViewModel(productsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productsViewModel.getProducts()
        setUpProductAdapter()
        observeProducts()
    }

    private fun observeProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            productsViewModel.getProducts().observe(viewLifecycleOwner) {
                productsAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun setUpProductAdapter() {
        productsAdapter = ProductsAdapter()
        binding.rvProducts.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}