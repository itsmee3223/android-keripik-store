package com.silvinda.keripikstore.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silvinda.keripikstore.sign.signin.SignInActivity
import com.silvinda.keripikstore.databinding.ActivityOnBoardingOneBinding
import com.silvinda.keripikstore.utils.Preferences

class OnboardingOneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingOneBinding
    private lateinit var preferences: Preferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingOneBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        preferences = Preferences(this)

        if(preferences.getValues("onboarding").equals("1")){
            finishAffinity()
            startActivity(Intent(this@OnboardingOneActivity, SignInActivity::class.java))
        }

        binding.ivHome.setOnClickListener {
            val intent = Intent(this@OnboardingOneActivity,
                SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
