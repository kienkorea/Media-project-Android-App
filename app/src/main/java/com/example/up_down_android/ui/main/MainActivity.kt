package com.example.up_down_android.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.up_down_android.R
import com.example.up_down_android.databinding.ActivityMainBinding
import com.example.up_down_android.util.setupWithNavController

class MainActivity : AppCompatActivity() {

    private val mainViewBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainViewBinding.root)
        if (savedInstanceState == null) setUpBottomNavigationBar()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setUpBottomNavigationBar()
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setUpBottomNavigationBar() {
        val navGraphIds = listOf(
            R.navigation.navigation_stock,
            R.navigation.navigation_community,
            R.navigation.navigation_info
        )

        mainViewBinding.bottomNavigation.setupWithNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.view_main,
            intent
        )
    }
}