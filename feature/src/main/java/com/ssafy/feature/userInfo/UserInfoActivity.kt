package com.ssafy.feature.userInfo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ssafy.feature.R
import com.ssafy.feature.databinding.ActivityUserInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoActivity : AppCompatActivity() {

    private lateinit var  binding : ActivityUserInfoBinding
    private val viewModel : UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityUserInfoBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.userInfoResult.observe(this){
            it.fold(
                onSuccess = { userInfo->
                    binding.userInfoName.text=userInfo.username
                    binding.userInfoMileage.text=userInfo.mileage.toString()
                    binding.userInfoLevel.text=userInfo.level.toString()


                },
                onFailure = {
                    binding.userInfoName.text="Unknown Error"
                }
            )
        }

        binding.userInfoGet.setOnClickListener {
            viewModel.fetchGet()
        }

        binding.userInfoPost.setOnClickListener {
            viewModel.fetchPost(3)
        }



    }
}