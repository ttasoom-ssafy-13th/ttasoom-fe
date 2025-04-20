package com.ssafy.domain.userInfo.repository

import com.ssafy.domain.userInfo.model.UserInfo

interface UserInfoRepository {
    suspend fun getUserInfo() : Result<UserInfo>
    // 마일리지와 등급 조회할 떄 ex) MyPage
    // 처음 로그인 하고 유저 정보 불러올 때
    // 이렇게 받아온 UserInfo는 local에 저장 예정

    suspend fun putUserInfo(userInfo : UserInfo): Result<UserInfo>
    // 마일리지나 등급 바꿀 때.
    // 등급 기준하고 마일리지 부여 기준은 클라이언트에서 처리해도 될 듯
    // local에서 userInfo 변경 후 바뀌었음을 알릴 때 사용


}