package com.ssafy.ttasoom

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.di.navigation.Navigator
import com.ssafy.feature.boiler.ui.BoilerSolutionFragment
import com.ssafy.ttasoom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.root.post {
            binding.bottomNav.setupWithNavController(navController)
        } // 하단바 버튼 누르면 버튼 대로 가게 하기
        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.visibility = when (destination.id) {
                R.id.loginFragment,
                R.id.registerFragment -> View.GONE
                else -> View.VISIBLE
            }
        }
    }


    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun toMain() {
        navController.navigate(R.id.action_loginFragment_to_communityFragment)
    }

    override fun toMyPage() {
//        navController.navigate()
    }

    override fun toWeather() {
        TODO("Not yet implemented")
    }

    override fun toBoilerSolution() {
        TODO("Not yet implemented")
    }

    override fun toCommunityBoard(post_id: String ,flag :Boolean) {
        val bundle = Bundle().apply {
            putString("post_id", post_id)
            putBoolean("flag", flag)
        }
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

    override fun toRegister() {
        navController.navigate(R.id.registerFragment)
    }

    override fun toLogin() {
        navController.navigate(R.id.loginFragment)
    }

    override fun toBoilerSolution() {
        navController.navigate(R.id.action_mypage_fragment_to_boiler_solution_fragment)
    }

}
