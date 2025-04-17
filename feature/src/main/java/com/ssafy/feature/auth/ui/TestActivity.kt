package com.ssafy.feature.auth.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.feature.auth.viewmodel.TestViewModel
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

       // val list= mutableListOf<Boiler>()

    }
}