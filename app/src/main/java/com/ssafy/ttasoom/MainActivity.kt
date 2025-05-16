package com.ssafy.ttasoom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
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
        binding.root.post {
            navController.popBackStack(navController.graph.startDestinationId, false) //backstack 없애는 코드라는데
            binding.bottomNav.setupWithNavController(navController)
        } // 하단바 버튼 누르면 버튼 대로 가게 하기
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

}
