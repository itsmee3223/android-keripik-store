package com.silvinda.keripikstore.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.silvinda.keripikstore.R
import com.silvinda.keripikstore.detail.DetailActivity
import com.silvinda.keripikstore.databinding.FragmentDashboardBinding
import com.silvinda.keripikstore.model.Keripik
import com.silvinda.keripikstore.utils.Preferences
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefrences: Preferences
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mDatabaseTerlaris: DatabaseReference

    private var dataListTerlaris = ArrayList<Keripik>()
    private var dataListLainnya = ArrayList<Keripik>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prefrences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance("https://keripik-store-cbf2a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Keripik")
        mDatabaseTerlaris = FirebaseDatabase.getInstance("https://keripik-store-cbf2a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Terlaris")


        binding.tvNama.text = prefrences.getValues("nama")

        if(!prefrences.getValues("saldo").equals("")) {
            currency(prefrences.getValues("saldo")!!.toDouble(), binding.tvSaldo)
        } else {
            binding.tvSaldo.text = "Rp0"
        }

        if(prefrences.getValues("url") == "") {
            binding.ivProfilePic.setImageResource(R.drawable.user_pic)
        } else {
            Glide.with(this)
                .load(prefrences.getValues("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfilePic)
        }

        binding.rvTerlaris.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvLainnya.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataListLainnya.clear()

                for(getDataSnap in dataSnapshot.children) {
                    val product = getDataSnap.getValue(Keripik::class.java)
                    dataListLainnya.add(product!!)
                }

                if(dataListLainnya.isNotEmpty()) {
                    binding.rvLainnya.adapter = ProductLainnyaAdapter(dataListLainnya){
                        startActivity(Intent(context, DetailActivity::class.java).putExtra("data", it))
                    }
                }
            }

            override fun onCancelled(err: DatabaseError) {
                Toast.makeText(context, ""+err.message, Toast.LENGTH_LONG).show()
            }

        })

        mDatabaseTerlaris.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataListTerlaris.clear()

                for(getDataSnap in dataSnapshot.children) {
                    val product = getDataSnap.getValue(Keripik::class.java)
                    dataListTerlaris.add(product!!)
                }

                if(dataListTerlaris.isNotEmpty()) {
                    binding.rvTerlaris.adapter = TerlarisAdapter(dataListTerlaris){
                        startActivity(Intent(context, DetailActivity::class.java).putExtra("data", it))
                    }
                }
            }

            override fun onCancelled(err: DatabaseError) {
                Toast.makeText(context, ""+err.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun currency(harga: Double, textView: TextView) {
        val localeId = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
        textView.text = formatRupiah.format(harga)
    }
}