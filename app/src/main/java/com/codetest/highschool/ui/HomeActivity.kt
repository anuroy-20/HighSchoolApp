package com.codetest.highschool.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.codetest.highschool.R
import com.codetest.highschool.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = supportFragmentManager.getNavController()
    }

    //This method will locate the navController associated with this fragment.
    private fun FragmentManager.getNavController(): NavController {
        return(findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}