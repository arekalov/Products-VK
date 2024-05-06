package com.arekalov.productsvk.fragments


import MyLoadStateAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.arekalov.productsvk.adapters.ProductsAdapter
import com.arekalov.productsvk.viewModels.ConnectionLiveData
import com.arekalov.productsvk.viewModels.HomeFragmentViewModel
import com.arekalov.productsvk.viewModels.factories.HomeFragmentViewModelFactory
import com.arekalov.productsvk.MainActivity
import com.arekalov.productsvk.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var connectionLiveData: ConnectionLiveData

    private val productsViewModel: HomeFragmentViewModel by viewModels {
        HomeFragmentViewModelFactory((activity as MainActivity).productsRepository)
    }
    private lateinit var productsAdapter: ProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
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
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            observeNetwork()
            delay(300)
            if (connectionLiveData.value == null || connectionLiveData.value == false) lostNetwork()
        }
        productsViewModel.getProducts()
        setUpProductAdapter()
        observeProducts()
        productOnClick()
        searchOnClick()
    }

    private fun observeNetwork() {
        connectionLiveData = ConnectionLiveData(requireActivity().application)
        connectionLiveData.observe(viewLifecycleOwner) {
        }
    }

    private fun lostNetwork() {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNoInternetFragment())
    }

    private fun searchOnClick() {
        binding.ivSearch.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            findNavController().navigate(action)
        }
    }

    private fun productOnClick() {
        productsAdapter.onClick = {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailProductFragment2(it)
            findNavController().navigate(action)
        }
    }

    private fun observeProducts() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                productsViewModel.getProducts()?.observe(viewLifecycleOwner) {
                    productsAdapter.submitData(lifecycle, it)
                }
            } catch (ex: Throwable) {
                Log.e("error", "observeProducts: ${ex.message}")
            }
        }
    }


    private fun setUpProductAdapter() {
        productsAdapter = ProductsAdapter()
        val footerAdapter = MyLoadStateAdapter()
        binding.rvProducts.adapter = productsAdapter.withLoadStateFooter(footerAdapter)
        val layManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        layManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                binding.progressBar.visibility = View.INVISIBLE
                return if (position == productsAdapter.itemCount && footerAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        binding.rvProducts.apply {
            layoutManager = layManager
            adapter = productsAdapter.withLoadStateFooter(MyLoadStateAdapter())
        }
    }

}
