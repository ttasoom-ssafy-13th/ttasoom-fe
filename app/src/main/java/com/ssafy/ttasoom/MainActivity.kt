package com.ssafy.ttasoom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ssafy.di.navigation.Navigator
import com.ssafy.feature.boiler.ui.BoilerFragment
import com.ssafy.feature.community.ui.CommunityFragment
import com.ssafy.ttasoom.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNav()

    }

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun toMain() {
        navController.navigate(R.id.action_loginFragment_to_boilerFragment)
    }

    override fun toCommunityBoard() {
        navController.navigate(R.id.action_communityFragment_to_communityBoardFragment)
    }

    override fun toCommunityEdit(isBoard: Boolean) {
        if (isBoard) {
            navController.navigate(R.id.action_communityBoardFragment_to_communityEditFragment)
        } else {
            navController.navigate(R.id.action_communityFragment_to_communityEditFragment)
        }
    }

    private fun initBottomNav() {
        binding.bottomNav.selectedItemId = R.id.nav_boiler

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_boiler -> {
                    loadFragment(BoilerFragment())
                    true
                }

                R.id.nav_community -> {
                    loadFragment(CommunityFragment())
                    true
                }

                else -> false
            }
        }

    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit()
    }


}
