package com.example.loginandregister.models

data class UserModel (
    val name:String,
    val email: String,
    val password:String)
{
    constructor(): this("","","")
}