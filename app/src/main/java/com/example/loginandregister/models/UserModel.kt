package com.example.loginandregister.models

data class UserModel (
    var uid:String,
    var full_name:String,
    val username:String,
    var email: String,
    val password:String,
    val alamat:String)
{
    constructor(): this("","","","","","")
}