package com.arekalov.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.data.network.ProductsNetworkService
import com.arekalov.data.network.ProductsRepository
import com.arekalov.presentation.adapters.CategoriesAdapter
import com.arekalov.presentation.databinding.FragmentCategoriesBinding
import com.arekalov.presentation.viewModels.CategoriesViewModel

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var repository: ProductsRepository
    private lateinit var categoryViewModel: CategoriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = ProductsRepository(ProductsNetworkService())
        categoryViewModel = CategoriesViewModel(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(inflater)
        categoryViewModel.getCategories()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareAdapter()
        observeCategoriesLiveData()
        onClickOnCategory()
    }

    private fun onClickOnCategory() {
        categoriesAdapter.onCLick = {
            val action =
                CategoriesFragmentDirections.actionCategoriesFragmentToProductsInCategoryFragment(it)
            findNavController().navigate(action)
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
            layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        }
    }
}