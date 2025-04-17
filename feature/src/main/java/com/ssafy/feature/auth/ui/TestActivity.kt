package com.ssafy.feature.auth.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.ssafy.domain.model.Boiler
import com.ssafy.feature.R
import com.ssafy.feature.auth.viewmodel.TestViewModel
import com.ssafy.feature.databinding.ActivityLoginBinding
import com.ssafy.feature.databinding.ActivityTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding
    private  val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        binding=ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val list= mutableListOf<Boiler>()
        val adapter =

    }
}