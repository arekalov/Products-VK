package com.arekalov.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.adapters.SearchAndCategoryProductsAdapter
import com.arekalov.presentation.viewModels.ConnectionLiveData
import com.arekalov.presentation.viewModels.SearchedViewModel
import com.arekalov.presentation.viewModels.factories.SearchedViewModelFactory
import com.arekalov.productsvk.R
import com.arekalov.productsvk.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var binding: FragmentSearchBinding
    private lateinit var productsAdapter: SearchAndCategoryProductsAdapter
    private val searchedViewModel: SearchedViewModel by viewModels {
        SearchedViewModelFactory(ProductsRepository(ProductsNetworkService()))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate( R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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
        lifecycleScope.launch {
            observeNetwork()
            delay(100)
            if (connectionLiveData.value == null || connectionLiveData.value == false) lostNetwork()
            else {
                prepareProductsAdapter()
                observeEditSearchLine()
                observeSearchedProducts()
                productSetOnClick()
            }
        }
    }

    private fun observeNetwork() {
        connectionLiveData = ConnectionLiveData(requireActivity().application)
        connectionLiveData.observe(viewLifecycleOwner) {
        }
    }

    private fun lostNetwork() {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToNoInternetFragment())
    }

    private fun productSetOnClick() {
        productsAdapter.onCLick = {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailProductFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun observeSearchedProducts() {
        Log.e("!!!!!!!!", "observe searched")
        viewLifecycleOwner.lifecycleScope.launch {
            if (connectionLiveData.value == false) {
                lostNetwork()
            } else {
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
    }

    private fun observeEditSearchLine() {
        var searchJob: Job? = null
        try {
            binding.etSearch.addTextChangedListener {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    if (connectionLiveData.value == false) {
                        lostNetwork()
                    } else {
                        searchedViewModel.searchProducts(it.toString())
                    }
                }
            }
        } catch (ex: Exception) {
            Log.e("error in SearchFragment", "observeEditSearchLine: error", )
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