package com.example.loginandregister.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.navigation.fragment.findNavController
import com.example.loginandregister.HomepageActivity
import com.example.loginandregister.R
import com.example.loginandregister.databinding.FragmentLoginBinding
import com.example.loginandregister.databinding.FragmentRegisterBinding
import com.example.loginandregister.databinding.FragmentWelcomeBinding
import com.google.firebase.auth.FirebaseAuth


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWelcomeBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            //For Activity
            val intent = Intent(activity, HomepageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            Handler().postDelayed({
                startActivity(intent)
            }, 1500)
        } else {
            Handler().postDelayed({
                findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment2)
            }, 1500)
        }
    }


}