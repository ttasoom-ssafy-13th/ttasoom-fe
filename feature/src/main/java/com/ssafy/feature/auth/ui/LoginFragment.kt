package com.ssafy.feature.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ssafy.di.navigation.Navigator
import com.ssafy.feature.auth.viewmodel.AuthViewModel
import com.ssafy.feature.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var navigator: Navigator

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigator.hide()  // 바텀 네비게이션 숨기기
        viewModel.verifyToken()

        binding.apply {
            viewModel.loginResult.observe(viewLifecycleOwner) { result ->
                result.fold(
                    onSuccess = { user ->
                        Toast.makeText(requireContext(), "환영합니다! ${user.email}", Toast.LENGTH_SHORT).show()
                        // Navigator 호출
                        navigator.toMain()
                    },
                    onFailure = { e ->
                        Toast.makeText(requireContext(), e.message ?: "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewModel.loginWithEmail(email, password)
            }

            binding.tvSignUp.setOnClickListener {
                (activity as? Navigator)?.toRegister()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }
}
