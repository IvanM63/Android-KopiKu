package com.example.loginandregister

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginandregister.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.core.Observable



@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //AUTH
        auth = FirebaseAuth.getInstance()


        //Register Button
        binding.registerButtonRegister.setOnClickListener {
            val email = binding.registerEmail.text.toString().trim()
            val password = binding.registerPassword.text.toString().trim()
            registerUser(email, password)
        }

    }

    private fun showNameExistAlert(alert: Boolean) {

        if(alert) {
            binding.registerFullName.error = "Form nama tidak boleh kosong!"
        } else {
            binding.registerFullName.error = null
        }
    }

    private fun showTextMinimalAlert(alert: Boolean, text:String) {
        if (text == "Username") {
            binding.registerUsername.error = if(alert) "$text harus lebih dari 4 huruf" else null
        } else if (text == "Password") {
            binding.registerPassword.error = if(alert) "$text harus lebih dari 8 huruf" else null
        }
    }

    private fun showEmailValidAlert(alert:Boolean) {
        binding.registerEmail.error = if(alert) "Email tidak valid!" else null
    }

    private fun showPasswordConfirmAlert(alert: Boolean) {
        binding.registerPassword.error = if(alert) "Password tidak sama!" else null
    }

    private fun registerUser(email:String, password:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if(it.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

}