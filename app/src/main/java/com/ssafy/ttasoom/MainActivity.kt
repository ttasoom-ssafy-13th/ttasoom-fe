package com.ssafy.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssafy.feature.boiler.ui.BoilerFragment
import com.ssafy.ttasoom.R
import com.ssafy.ttasoom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 프래그먼트 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.navHostFragment.id, BoilerFragment())
                .commit()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_boiler -> BoilerFragment()
//                R.id.nav_profile -> ProfileFragment()
                else -> BoilerFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(binding.navHostFragment.id, fragment)
                .commit()
            true
        }
    }
}
