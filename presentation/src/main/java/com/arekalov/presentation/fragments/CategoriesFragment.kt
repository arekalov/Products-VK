package com.arekalov.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.R
import com.arekalov.presentation.adapters.CategoryAdapter
import com.arekalov.presentation.databinding.FragmentCategoriesBinding
import com.arekalov.presentation.viewModels.CategoryViewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoryAdapter
    private lateinit var repository: ProductsRepository
    private lateinit var categoryViewModel: CategoryViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ProductsRepository(ProductsNetworkService())
        categoryViewModel = CategoryViewModel(repository)
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
        prepareAdapter()
        observeCategoriesLiveData()
        onClickOnCategory()
        categoryViewModel.getCategories()
    }

    private fun onClickOnCategory() {
        categoriesAdapter.onCLick = {
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToProductsInCategoryFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun observeCategoriesLiveData() {
        categoryViewModel.observeSearchedViewModel().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.differ.submitList(categories)

        }
    }

    private fun prepareAdapter() {
        categoriesAdapter = CategoryAdapter()
        binding.rvCategories.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}