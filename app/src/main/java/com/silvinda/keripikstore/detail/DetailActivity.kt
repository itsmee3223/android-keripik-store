package com.silvinda.keripikstore.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.silvinda.keripikstore.checkout.CheckoutSuccessActivity
import com.silvinda.keripikstore.databinding.ActivityDetailBinding
import com.silvinda.keripikstore.model.Keripik
import java.text.NumberFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var mDatabase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val data = intent.getParcelableExtra<Keripik>("data")
        mDatabase = FirebaseDatabase.getInstance().getReference("Keripik")
            .child(data?.judul.toString())

        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)

        binding.tvTitle.text = data?.judul
        binding.tvGenre.text = data?.kategori
        binding.tvDesc.text = data?.desc
        binding.tvRate.text = data?.rating
        binding.tvPrice.text = formatRupiah.format(data?.price!!.toDouble())

        Glide.with(this)
            .load(data?.poster)
            .into(binding.ivPoster)

        binding.btnBeli.setOnClickListener {
            startActivity(Intent(this@DetailActivity, CheckoutSuccessActivity::class.java))
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun currency(harga: Double, textView: TextView) {
        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
        textView.text = formatRupiah.format(harga)
    }
}