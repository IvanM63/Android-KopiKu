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
import com.example.loginandregister.databinding.FragmentLoginBinding
import com.example.loginandregister.databinding.FragmentRegisterBinding
import com.example.loginandregister.models.UserModel
import com.google.firebase.auth.FirebaseAuth


private val TAG = "RegisterFragment"

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        binding.apply {
            registerButtonRegister.setOnClickListener{
                if(
                    registerEmail.text.isNotEmpty() &&
                    registerPassword.text.isNotEmpty()
                ) {
                    val user = UserModel(
                        registerFullName.text.toString().trim(),
                        registerEmail.text.toString().trim(),
                        registerPassword.text.toString().trim()
                    )
                    registerUser(user.email, user.password)
                }
            }
        }

    }

    private fun registerUser(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Toast.makeText(requireActivity() , "Sukses Membuat Akun" , Toast.LENGTH_SHORT).show()

                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_registerFragment2_to_loginFragment2)

                } else {
                    Toast.makeText(requireActivity() , task.exception!!.localizedMessage!! , Toast.LENGTH_SHORT).show()

                }


            }

    }

}