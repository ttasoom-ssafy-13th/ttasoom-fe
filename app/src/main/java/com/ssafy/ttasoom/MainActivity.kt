package com.ssafy.ttasoom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ssafy.di.navigation.Navigator

import com.ssafy.ttasoom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.visibility = when (destination.id) {
                R.id.loginFragment -> View.GONE
                else -> View.VISIBLE
            }
        }
    }





    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun toMain() {
        navController.navigate(R.id.action_loginFragment_to_mypage_fragment)
    }

    override fun toMyPage() {
//        navController.navigate()
    }

    override fun toCommunityBoard(post_id: String) {
        val bundle = Bundle().apply { putString("post_id", post_id) }
        navController.navigate(R.id.action_communityFragment_to_communityBoardFragment, bundle)
    }

    override fun toCommunityEdit(
        isBoard: Boolean,
        post_id: String,
        title: String,
        content: String
    ) {
        val bundle = Bundle().apply {
            putBoolean("isBoard", isBoard)
            putString("post_id", post_id)
            putString("title", title)
            putString("content", content)
        }
        if (isBoard) {
            navController.navigate(
                R.id.action_communityBoardFragment_to_communityEditFragment,
                bundle
            )
        } else {
            navController.navigate(R.id.action_communityFragment_to_communityEditFragment, bundle)
        }
    }

    override fun toPrev() {
        navController.popBackStack()
    }

    override fun hide() {
        binding.bottomNav.visibility = View.GONE
    }

    override fun show() {
        binding.bottomNav.visibility = View.VISIBLE
    }

    override fun toWeather() {
        navController.navigate(R.id.action_mypageFragment_to_weatherFragment)
    }
}
