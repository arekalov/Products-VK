package com.arekalov.productsvk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.arekalov.data.network.ProductsRepository
import com.arekalov.productsvk.adapters.CategoriesAdapter
import com.arekalov.productsvk.adapters.MyLoadStateAdapter
import com.arekalov.productsvk.adapters.ProductsAdapter
import com.arekalov.productsvk.adapters.SearchAndCategoryProductsAdapter
import com.arekalov.productsvk.di.DaggerAppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var productsRepository: ProductsRepository
    @Inject
    lateinit var searchAndCategoryProductsAdapter: SearchAndCategoryProductsAdapter
    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter
    @Inject
    lateinit var productsAdapter: ProductsAdapter
    @Inject
    lateinit var myLoadStateAdapter: MyLoadStateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerAppComponent.create().inject(this)
        setNavigation()

    }

    private fun setNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigation.setupWithNavController(navController)
    }
}