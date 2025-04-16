package com.ssafy.feature.boiler.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.feature.boiler.viewmodel.BoilerViewModel
import com.ssafy.feature.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoilerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: BoilerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // LiveData 관찰
        viewModel.boilerList.observe(this) { list ->
            val result = list.joinToString("\n") { boiler ->
                "${boiler.name} (${boiler.company}) - ${boiler.certification}, ${boiler.circulationType}, ${boiler.fuel}"
            }
            binding.tv.text = result
        }

        // 전체 목록 불러오기 버튼
        binding.button1.setOnClickListener {
            viewModel.loadAllBoilers()
        }

        // 필터링 예시 버튼 (예: "린나이코리아", "2종", "개방식", "LNG")
        binding.button2.setOnClickListener {
            viewModel.loadFilteredBoilers("린나이코리아(주)", "2종", "개방식", "LNG")
        }
    }
}
