package com.example.loginandregister

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.loginandregister.databinding.ActivityHomepageBinding
import com.google.firebase.auth.FirebaseAuth


class HomepageActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding:ActivityHomepageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //AUTH
        auth = FirebaseAuth.getInstance()

        //NAV BAR
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController

        setupWithNavController(binding.bottomNavigationView, navController)

        //Testing User
        Toast.makeText(this, "Welcome ${auth.currentUser!!.email}", Toast.LENGTH_SHORT).show()

        //Logout Button
        /*binding.homepageButtonLogout.setOnClickListener {
            auth.signOut()
            Intent(this, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(this,"Sign Out Berhasil", Toast.LENGTH_SHORT).show()
            }
        }*/

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}