package com.ssafy.ttasoom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.feature.auth.ui.LoginActivity
import com.ssafy.feature.boiler.ui.BoilerTestActivity
import com.ssafy.feature.userInfo.UserInfoActivity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, UserInfoActivity::class.java)) //임시
        finish()
    }
}