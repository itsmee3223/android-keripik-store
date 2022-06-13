package com.silvinda.keripikstore.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.silvinda.keripikstore.R
import com.silvinda.keripikstore.databinding.ActivityHomeScreenBinding
import com.silvinda.keripikstore.home.dashboard.DashboardFragment
import com.silvinda.keripikstore.home.settings.SettingFragment
import com.silvinda.keripikstore.home.history.HistoryFragment

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentTiket = HistoryFragment()
        val fragmentSetting = SettingFragment()
        val fragmentHome = DashboardFragment()

        setFragment(fragmentHome)

        binding.ivMenu1.setOnClickListener {
            setFragment(fragmentTiket)
            changeIcon(binding.ivMenu1, R.drawable.ic_history_active)
            changeIcon(binding.ivMenu2, R.drawable.ic_home)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile)
        }
        binding.ivMenu2.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(binding.ivMenu1, R.drawable.ic_history)
            changeIcon(binding.ivMenu2, R.drawable.ic_home_active)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile)
        }
        binding.ivMenu3.setOnClickListener {
            setFragment(fragmentSetting)
            changeIcon(binding.ivMenu1, R.drawable.ic_history)
            changeIcon(binding.ivMenu2, R.drawable.ic_home)
            changeIcon(binding.ivMenu3, R.drawable.ic_profile_active)
        }
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }
}