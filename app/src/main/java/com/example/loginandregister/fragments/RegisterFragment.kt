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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


private val TAG = "RegisterFragment"

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth

    private val userDatabaseReference = Firebase.firestore.collection("users")

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
                        "",
                        registerFullName.text.toString().trim(),
                        registerUsername.text.toString().trim(),
                        registerEmail.text.toString().trim(),
                        registerPassword.text.toString().trim(),
                        "Belum ada alamat"
                    )
                    //user.uid = auth.currentUser!!.uid
                    registerUser(user)

                }
            }
        }

    }

    private fun addDataToUserModel(user: UserModel) {
        userDatabaseReference.add(user).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(requireContext(), "Berhasil Menambahkan User ke Collection", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), task.exception!!.localizedMessage!!, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(user: UserModel) {

        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    Toast.makeText(requireActivity() , "Sukses Membuat Akun" , Toast.LENGTH_SHORT).show()
                    addDataToUserModel(user)

                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_registerFragment2_to_loginFragment2)

                } else {
                    Toast.makeText(requireActivity() , task.exception!!.localizedMessage!! , Toast.LENGTH_SHORT).show()

                }


            }

    }

}