package com.ssafy.feature.boiler.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.feature.databinding.ActivityBoilerTestBinding
import com.ssafy.feature.boiler.viewmodel.BoilerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoilerTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBoilerTestBinding
    private val viewModel: BoilerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoilerTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.boilerListResult.observe(this) { result ->
            result.fold(
                onSuccess = { list ->
                    Log.d("BoilerTest", "불러온 보일러 수: ${list.size}")
                    list.forEach {
                        Log.d("BoilerTest", "${it.companyName} / ${it.productName}")
                    }
                    binding.textView.text = "불러온 보일러 수: ${list.size}"
                },
                onFailure = { e ->
                    Log.e("BoilerTest", "에러 발생: ${e.message}")
                    binding.textView.text = "에러: ${e.message}"
                }
            )
        }

        binding.button.setOnClickListener {
            viewModel.fetchBoilers()
        }

        binding.filterButton.setOnClickListener {
            val company = binding.etCompanyName.text.toString().ifBlank { null }
            val cert = binding.etCertificationType.text.toString().ifBlank { null }
            val circ = binding.etCirculationType.text.toString().ifBlank { null }
            val fuel = binding.etFuelType.text.toString().ifBlank { null }

            viewModel.fetchFilteredBoilers(
                companyName = company,
                certificationType = cert,
                circulationType = circ,
                fuelType = fuel
            )
        }

    }
}
