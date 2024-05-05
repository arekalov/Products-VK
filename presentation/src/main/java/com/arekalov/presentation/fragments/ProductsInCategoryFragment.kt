package com.arekalov.presentation.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.R
import com.arekalov.presentation.adapters.SearchAndCategoryProductsAdapter
import com.arekalov.presentation.databinding.FragmentProductsInCategoryBinding
import com.arekalov.presentation.viewModels.ConnectionLiveData
import com.arekalov.presentation.viewModels.SearchedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProductsInCategoryFragment : Fragment() {
    private lateinit var binding: FragmentProductsInCategoryBinding
    private lateinit var productsInCategoryAdapter: SearchAndCategoryProductsAdapter
    private lateinit var productsInCategoryViewModel: SearchedViewModel
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var repository: ProductsRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ProductsRepository(ProductsNetworkService())
        productsInCategoryViewModel = SearchedViewModel(repository)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.categoriesFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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
        lifecycleScope.launch {
            observeNetwork()
            delay(100)
            if (connectionLiveData.value == null || connectionLiveData.value == false) {lostNetwork()}
        }
        prepareAdapter()
        observeLiveData()
        onClickOnProduct()
        val category = arguments?.getString("category")
        if (category != null) {
            binding.tvCategory.text = category.capitalize()
            productsInCategoryViewModel.getProductByCategory(category)
        }
        else {
            Log.e(TAG, "onViewCreated: null arg")
        }
    }

    private fun observeNetwork() {
        connectionLiveData = ConnectionLiveData(requireActivity().application)
        connectionLiveData.observe(viewLifecycleOwner) {
        }
    }

    private fun lostNetwork() {
        findNavController().navigate(ProductsInCategoryFragmentDirections.actionProductsInCategoryFragmentToNoInternetFragment())
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