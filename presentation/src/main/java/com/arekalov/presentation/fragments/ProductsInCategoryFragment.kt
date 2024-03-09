package com.arekalov.presentation.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.R
import com.arekalov.presentation.adapters.SearchAndCategoryProductsAdapter
import com.arekalov.presentation.databinding.FragmentProductsInCategoryBinding
import com.arekalov.presentation.viewModels.SearchedViewModel

class ProductsInCategoryFragment : Fragment() {
    private lateinit var binding: FragmentProductsInCategoryBinding
    private lateinit var productsInCategoryAdapter: SearchAndCategoryProductsAdapter
    private lateinit var productsInCategoryViewModel: SearchedViewModel
    private lateinit var repository: ProductsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ProductsRepository(ProductsNetworkService())
        productsInCategoryViewModel = SearchedViewModel(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductsInCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareAdapter()
        observeLiveData()
        onClickOnProduct()
        val category = arguments?.getString("category")
        if (category != null) {
            productsInCategoryViewModel.getProductByCategory(category)
        }
        else {
            Log.e(TAG, "onViewCreated: null arg")
        }
    }

    private fun onClickOnProduct() {
        productsInCategoryAdapter.onCLick = {
            val action = ProductsInCategoryFragmentDirections.actionProductsInCategoryFragmentToDetailProductFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun observeLiveData() {
        productsInCategoryViewModel.observeSearchedViewModel().observe(viewLifecycleOwner, Observer {
            products->
            productsInCategoryAdapter.differ.submitList(products)
        })
    }

    private fun prepareAdapter() {
        productsInCategoryAdapter = SearchAndCategoryProductsAdapter()
        binding.rvProducts.apply {
            adapter = productsInCategoryAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}