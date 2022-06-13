package com.silvinda.keripikstore.home

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.silvinda.keripikstore.databinding.ActivityMyWalletBinding
import com.silvinda.keripikstore.home.wallet.MyWalletTopUpActivity
import com.silvinda.keripikstore.home.wallet.WalletAdapter
import com.silvinda.keripikstore.model.Wallet
import com.silvinda.keripikstore.utils.Preferences
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


class MyWalletScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyWalletBinding

    private var dataList = ArrayList<Wallet>()
    private lateinit var prefrences: Preferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyWalletBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        prefrences = Preferences(applicationContext)
        if(!prefrences.getValues("saldo").equals("")) {
            currency(prefrences.getValues("saldo")!!.toDouble(), binding.textView25)
        } else {
            binding.textView25.text = "Rp0"
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        loadDummyData()
    }

    private fun loadDummyData() {
        dataList.add(
            Wallet(
                "Keripik Balado",
                "Sabtu 12 Jan, 2022",
                700000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Top Up",
                "Sabtu 12 Jan, 2020",
                1700000.0,
                "1"
            )
        )
        dataList.add(
            Wallet(
                "Zanana Chips",
                "Sabtu 12 Jan, 2022",
                700000.0,
                "0"
            )
        )

        initListener()
    }

    private fun initListener() {
        binding.rvTransaksi.layoutManager = LinearLayoutManager(this)
        binding.rvTransaksi.adapter = WalletAdapter(dataList){

        }

        binding.btnTopUp.setOnClickListener {
            startActivity(Intent(this, MyWalletTopUpActivity::class.java))
        }
    }

    private fun currency(harga: Double, textView: TextView) {
        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
        textView.text = formatRupiah.format(harga)
    }
}