package com.ssafy.di.navigation

/**
 * 화면 전환을 추상화한 인터페이스
 */
interface Navigator {
    /** 로그인 → 메인 화면으로 */
    fun toMain()

    fun toCommunityBoard() //커뮤니티 리스트 -> 특정 게시글

    //커뮤니티 리스트 or 커뮤니티 게시글 -> 글 작성 및 수정
    //isBoard가 true면 게시글에서 글 작성으로 이동하는 경우
    fun toCommunityEdit( isBoard : Boolean = false)

}
