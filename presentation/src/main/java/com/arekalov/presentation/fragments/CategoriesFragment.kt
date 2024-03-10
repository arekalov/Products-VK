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
import com.arekalov.presentation.adapters.CategoriesAdapter
import com.arekalov.presentation.databinding.FragmentCategoriesBinding
import com.arekalov.presentation.viewModels.CategoriesViewModel
import com.arekalov.presentation.viewModels.ConnectionLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var repository: ProductsRepository
    private lateinit var categoryViewModel: CategoriesViewModel
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ProductsRepository(ProductsNetworkService())
        categoryViewModel = CategoriesViewModel(repository)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            observeNetwork()
            delay(300)
            if (connectionLiveData.value == null || connectionLiveData.value == false) lostNetwork()
        }
        prepareAdapter()
        observeCategoriesLiveData()
        onClickOnCategory()
    }

    private fun observeNetwork() {
        connectionLiveData = ConnectionLiveData(requireActivity().application)
        connectionLiveData.observe(viewLifecycleOwner) {
        }
    }

    private fun lostNetwork() {
        findNavController().navigate(CategoriesFragmentDirections.actionCategoriesFragmentToNoInternetFragment())
    }


    private fun onClickOnCategory() {
        categoriesAdapter.onCLick = {
            if (connectionLiveData.value == false) {
                lostNetwork()
            } else {
                val action =
                    CategoriesFragmentDirections.actionCategoriesFragmentToProductsInCategoryFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun observeCategoriesLiveData() {
        categoryViewModel.observeSearchedViewModel().observe(viewLifecycleOwner, Observer {   categories ->
            categoriesAdapter.differ.submitList(categories)
        })
    }

    private fun prepareAdapter() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}