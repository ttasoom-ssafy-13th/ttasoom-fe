package com.ssafy.feature.mypage.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.feature.databinding.FragmentMypageBinding
import com.ssafy.feature.mypage.viewmodel.MypageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MypageFragment : Fragment() {

    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MypageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 데이터 로드
        viewModel.loadMileageStatus()

        // 데이터 관찰
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.mileageStatus.collectLatest { status ->
                status?.let {
                    binding.tvName.text = "안녕하세요 ${it.userId}님"
                    binding.tvGrade.text = it.grade
                    binding.tvGreeting.text = "다시 오신 것을 환영합니다! 자가검침을 하고 보상을 받으세요\n총 마일리지 포인트: ${it.totalMileage}"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
