package com.example.loginandregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.loginandregister.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //AUTH
        auth = FirebaseAuth.getInstance()

        //LoginButton
        /*binding.homeLogin.setOnClickListener {
            startActivity(Intent(this, HomepageActivity::class.java))
        }*/

        //Register Button
        /*binding.homeRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }*/

    }

    override fun onStart() {
        super.onStart()
        /*if(auth.currentUser != null) {
            Intent(this, HomepageActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }*/
    }
}