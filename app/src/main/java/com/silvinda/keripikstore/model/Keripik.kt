package com.silvinda.keripikstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Keripik (
    var desc: String ?="",
    var kategori: String ?="",
    var judul: String ?="",
    var poster: String ?="",
    var rating: String ?="",
    var price: String ?=""
    ): Parcelable