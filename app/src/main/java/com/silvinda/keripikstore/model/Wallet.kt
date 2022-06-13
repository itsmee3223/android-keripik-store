package com.silvinda.keripikstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Wallet (
    var title: String ?="",
    var date: String ?="",
    var money: Double ,
    var status: String ?=""
): Parcelable