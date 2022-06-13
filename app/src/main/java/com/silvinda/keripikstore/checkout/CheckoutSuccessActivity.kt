package com.silvinda.keripikstore.checkout
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.silvinda.keripikstore.databinding.ActivityCheckoutSuccessBinding
import com.silvinda.keripikstore.home.HomeScreenActivity

class CheckoutSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this, HomeScreenActivity::class.java))
        }
    }
}