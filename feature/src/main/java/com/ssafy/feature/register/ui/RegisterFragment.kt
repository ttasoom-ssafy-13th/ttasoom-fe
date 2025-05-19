package com.ssafy.feature.auth.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ssafy.di.navigation.Navigator
import com.ssafy.domain.auth.model.UserRegisterInfo
import com.ssafy.feature.auth.viewmodel.RegisterViewModel
import com.ssafy.feature.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


private const val TAG = "RegisterFragment"
@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()

            if (email.isBlank() || password.isBlank() || name.isBlank() || phone.isBlank()) {
                Toast.makeText(context, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = UserRegisterInfo(email, password, name, phone)
            viewModel.register(user)
        }

        binding.tvLogin.setOnClickListener {
            (activity as? Navigator)?.toLogin()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.registerResult.collectLatest { result ->
                result?.onSuccess {
                    Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    (activity as? Navigator)?.toLogin()
                }?.onFailure {
                    Toast.makeText(context, "회원가입 실패: ${it.message}", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "onViewCreated: ${it.message}")
                }
                viewModel.resetResult()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
