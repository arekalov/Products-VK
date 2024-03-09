package com.arekalov.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.R
import com.arekalov.presentation.adapters.ProductsAdapter
import com.arekalov.presentation.adapters.SearchAndCategoryProductsAdapter
import com.arekalov.presentation.databinding.FragmentSearchBinding
import com.arekalov.presentation.viewModels.SearchedViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var productsAdapter: SearchAndCategoryProductsAdapter
    private lateinit var repository: ProductsRepository
    private lateinit var searchedViewModel: SearchedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ProductsRepository(ProductsNetworkService())
        searchedViewModel = SearchedViewModel(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareProductsAdapter()
        observeEditSearchLine()
        observeSearchedProducts()
        productSetOnClick()
    }

    private fun productSetOnClick() {
        productsAdapter.onCLick = {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailProductFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun observeSearchedProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(500)
            searchedViewModel.observeSearchedViewModel().observe(viewLifecycleOwner, Observer{ products ->
                if (products.isNotEmpty()) {
                    binding.ivNotFound.visibility = View.INVISIBLE
                    binding.rvProducts.visibility = View.VISIBLE
                    productsAdapter.differ.submitList(products)
                }
                else {
                    binding.rvProducts.visibility = View.INVISIBLE
                    binding.ivNotFound.visibility = View.VISIBLE
                }
            })
        }
    }

    private fun observeEditSearchLine() {
        var searchJob: Job? = null
        binding.etSearch.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                searchedViewModel.searchProducts(it.toString())
            }
        }
    }

    private fun prepareProductsAdapter() {
        productsAdapter = SearchAndCategoryProductsAdapter()
        binding.rvProducts.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }

}