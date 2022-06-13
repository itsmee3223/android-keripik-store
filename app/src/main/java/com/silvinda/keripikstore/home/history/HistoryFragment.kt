package com.silvinda.keripikstore.home.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.silvinda.keripikstore.databinding.FragmentHistoryBinding
import com.silvinda.keripikstore.home.dashboard.ProductLainnyaAdapter
import com.silvinda.keripikstore.model.Keripik
import com.silvinda.keripikstore.utils.Preferences
import kotlin.collections.ArrayList

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefrences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Keripik>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prefrences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance("https://keripik-store-cbf2a-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("History")

        binding.rcHistory.layoutManager = LinearLayoutManager(requireContext().applicationContext)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()

                for(getDataSnap in dataSnapshot.children) {
                    val keripik = getDataSnap.getValue(Keripik::class.java)
                    dataList.add(keripik!!)
                }
                binding.rcHistory.adapter = ProductLainnyaAdapter(dataList){

                }
            }


            override fun onCancelled(err: DatabaseError) {
                Toast.makeText(context, ""+err.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}