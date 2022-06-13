package com.silvinda.keripikstore.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.silvinda.keripikstore.R
import com.silvinda.keripikstore.model.Keripik
import java.text.NumberFormat
import java.util.*

class TerlarisAdapter(private var data: List<Keripik>,
                      private var listener: (Keripik) -> Unit) : RecyclerView.Adapter<TerlarisAdapter.LeagueViewHolder>() {

    private lateinit var contextAdapter : Context
    class LeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val tvKategori: TextView = view.findViewById(R.id.tv_kategori)
        private val tvRate: TextView = view.findViewById(R.id.tv_rate)
        private val tvPrice: TextView = view.findViewById(R.id.tv_price)

        private  val tvImage: ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data: Keripik, listener: (Keripik) -> Unit, context: Context) {
            tvTitle.text = data.judul
            tvKategori.text = data.kategori
            tvRate.text = data.rating
            currency(data.price!!.toDouble(), tvPrice)

            Glide.with(context)
                .load(data.poster)
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }

        private fun currency(harga: Double, textView: TextView) {
            val localeId = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeId)
            textView.text = formatRupiah.format(harga)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflateView: View = layoutInflater.inflate(R.layout.terlaris_item, parent, false)
        return  LeagueViewHolder(inflateView)

    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
