package com.arekalov.productsvk.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.productsvk.adapters.CategoriesAdapter
import com.arekalov.productsvk.viewModels.CategoriesViewModel
import com.arekalov.productsvk.viewModels.ConnectionLiveData
import com.arekalov.productsvk.viewModels.factories.CategoriesViewModelFactory
import com.arekalov.productsvk.MainActivity
import com.arekalov.productsvk.R
import com.arekalov.productsvk.databinding.FragmentCategoriesBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesAdapter
    private val categoryViewModel: CategoriesViewModel by viewModels {
        CategoriesViewModelFactory((activity as MainActivity).productsRepository)
    }
    private lateinit var connectionLiveData: ConnectionLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.progressBar.visibility = View.VISIBLE
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
            binding.progressBar.visibility = View.INVISIBLE
        })
    }

    private fun prepareAdapter() {
        categoriesAdapter = (activity as MainActivity).categoriesAdapter
        binding.rvCategories.apply {
            adapter = categoriesAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }
}