package com.silvinda.keripikstore.home.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.silvinda.keripikstore.R
import com.silvinda.keripikstore.databinding.FragmentSettingsBinding
import com.silvinda.keripikstore.home.MyWalletScreenActivity
import com.silvinda.keripikstore.utils.Preferences

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefrences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prefrences = Preferences(requireContext().applicationContext)

        binding.tvNama.text = prefrences.getValues("nama")
        binding.tvEmail.text = prefrences.getValues("email")
        val urlPhoto = prefrences.getValues("url")

        if(urlPhoto == ""){
            binding.ivProfilePic.setImageResource(R.drawable.user_pic)
        } else {
            Glide.with(this)
                .load(prefrences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfilePic)
        }

        binding.tvWallet.setOnClickListener {
            startActivity(Intent(activity, MyWalletScreenActivity::class.java))
        }
    }
}