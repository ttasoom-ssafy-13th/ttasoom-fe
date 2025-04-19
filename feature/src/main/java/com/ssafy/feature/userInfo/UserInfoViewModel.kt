package com.ssafy.feature.userInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.domain.userInfo.model.UserInfo
import com.ssafy.domain.userInfo.usercase.GetUserInfoUseCase
import com.ssafy.domain.userInfo.usercase.PostUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject  constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val postUserInfoUseCase: PostUserInfoUseCase
) : ViewModel(){
    private val _userInfoResult = MutableLiveData<Result<UserInfo>>()

    val userInfoResult : LiveData<Result<UserInfo>> = _userInfoResult

    fun fetchGet(){
        viewModelScope.launch {
            val result=getUserInfoUseCase()
            _userInfoResult.value=result
        }
    }

    fun fetchPost(
        mileageType : Int
    ){
        viewModelScope.launch {
            val result=postUserInfoUseCase(mileageType)
            _userInfoResult.value=result
        }
    }



}