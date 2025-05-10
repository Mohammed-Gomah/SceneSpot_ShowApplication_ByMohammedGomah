package com.example.scenespotnersion2.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

//        binding.bottomNavigationView.setOnItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.homeFragment -> {
//                    findNavController(R.id.fragmentContainerView).navigate(R.id.homeFragment, null, NavOptions.Builder().setPopUpTo(R.id.homeFragment, true).build()
//                    )
//                    true
//                }
//
//                R.id.favouritesFragment -> {
//                    findNavController(R.id.fragmentContainerView).navigate(R.id.favouritesFragment)
//                    true
//                }
//
//                R.id.profileFragment -> {
//                    findNavController(R.id.fragmentContainerView).navigate(R.id.profileFragment)
//                    true
//                }
//
//                R.id.searchFragment -> {
//                    findNavController(R.id.fragmentContainerView).navigate(R.id.searchFragment)
//                    true
//                }
//
//                else -> false
//            }
//    }
//

    }
}