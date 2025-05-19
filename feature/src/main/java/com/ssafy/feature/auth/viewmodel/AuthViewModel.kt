package com.ssafy.feature.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        viewModelScope.launch {
            val result = loginUseCase.execute(email, password)
            _loginResult.value = result
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginUseCase.logout()
            _loginResult.value = Result.failure(Exception("Logged out"))
        }
    }

    fun verifyToken() {
        viewModelScope.launch {
            val result = loginUseCase.verifyToken()
            _loginResult.value = result
        }
    }
}
