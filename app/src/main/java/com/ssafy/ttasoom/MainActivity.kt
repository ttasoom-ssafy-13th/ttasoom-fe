package com.ssafy.ttasoom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.feature.auth.ui.LoginActivity
import com.ssafy.feature.boiler.ui.BoilerTestActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, BoilerTestActivity::class.java))
        finish()
    }
}