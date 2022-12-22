package com.example.loginandregister.models

data class UserModel (
    var uid:String,
    val full_name:String,
    val username:String,
    val email: String,
    val password:String,
    val alamat:String)
{
    constructor(): this("","","","","","")
}