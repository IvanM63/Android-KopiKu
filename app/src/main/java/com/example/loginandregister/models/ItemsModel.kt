package com.example.loginandregister.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemsModel(
    val id: String,
    val name:String,
    val type: String,
    val description: String? = null,
    val rating:String,
    val price:String,
    val img_url:String): Parcelable {

    constructor(): this("0", "", "","","","","")
}