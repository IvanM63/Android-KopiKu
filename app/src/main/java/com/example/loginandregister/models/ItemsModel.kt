package com.example.loginandregister.models


data class ItemsModel(
    val id: String,
    val name:String,
    val type: String,
    val description: String? = null,
    val rating:String,
    val price:String,
    val img_url:String) {

    constructor(): this("0", "", "","","","","")
}