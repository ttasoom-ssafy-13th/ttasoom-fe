package com.ssafy.feature.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.auth.model.AuthProviderType
import com.ssafy.domain.auth.model.User
import com.ssafy.domain.auth.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginResult = MutableLiveData<Result<User>>()
    val loginResult: LiveData<Result<User>> = _loginResult

    fun loginWithEmail(email: String, password: String) {
        val token = "$email|$password"
        login(AuthProviderType.EMAIL, token)
    }

    fun loginWithGoogle(idToken: String) {
        login(AuthProviderType.GOOGLE, idToken)
    }

    private fun login(type: AuthProviderType, token: String?) {
        viewModelScope.launch {
            val result = loginUseCase.execute(type, token)
            _loginResult.value = result
        }
    }
}
