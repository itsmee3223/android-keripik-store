package com.silvinda.keripikstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.silvinda.keripikstore.onboarding.OnboardingOneActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper())
            .postDelayed({
                startActivity(Intent(this@SplashScreenActivity, OnboardingOneActivity::class.java))
                finish()
            }, 2000)

    }
}