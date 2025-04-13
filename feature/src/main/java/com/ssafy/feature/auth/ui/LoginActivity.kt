package com.ssafy.feature.auth.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.feature.auth.viewmodel.AuthViewModel
import com.ssafy.feature.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            viewModel.loginResult.observe(this@LoginActivity) { result ->
                result.fold(
                    onSuccess = { user ->
                        tv.text = "환영합니다, ${user.email}님!"
                    },
                    onFailure = { e ->
                        tv.text = "로그인 실패: ${e.message}"
                    }
                )
            }

            button1.setOnClickListener {
                viewModel.loginWithEmail("abc@email.com", "password")
            }

            button2.setOnClickListener {
                viewModel.loginWithGoogle("idToken")
            }
        }


    }

}
