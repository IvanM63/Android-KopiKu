package com.example.loginandregister.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginandregister.HomepageActivity
import com.example.loginandregister.R
import com.example.loginandregister.databinding.FragmentHomeBinding
import com.example.loginandregister.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.apply {
            loginButtonLogin.setOnClickListener{
                val email = loginEmail.text.toString().trim()
                val password = loginPassword.text.toString().trim()
                signinUser(email, password)
            }
            loginButtonRegister.setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(R.id.action_loginFragment2_to_registerFragment2)
            }
            loginForgotPassword.setOnClickListener {
                Toast.makeText(requireActivity(),"Fitur ini belum tersedia...", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun signinUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Toast.makeText(requireActivity() , "Login Sukses" , Toast.LENGTH_SHORT).show()

                    //For Activity
                    val intent = Intent(activity, HomepageActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                    //For fragment
                    /*Navigation.findNavController(requireActivity(), R.id.fragmentContainerView)
                        .navigate(R.id.action_signInFragmentFragment_to_mainFragment)*/
                } else {
                    Toast.makeText(requireActivity() , task.exception!!.localizedMessage!! , Toast.LENGTH_SHORT).show()

                }


            }

    }

}