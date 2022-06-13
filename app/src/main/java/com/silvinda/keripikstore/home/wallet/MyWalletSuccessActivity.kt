package com.silvinda.keripikstore.home.wallet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.silvinda.keripikstore.databinding.ActivityMyWalletSuccessBinding
import com.silvinda.keripikstore.home.HomeScreenActivity

class MyWalletSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyWalletSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWalletSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this@MyWalletSuccessActivity, HomeScreenActivity::class.java))
        }
    }
}