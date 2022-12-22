package com.example.loginandregister.fragments.homepage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginandregister.HomepageActivity
import com.example.loginandregister.MainActivity
import com.example.loginandregister.R
import com.example.loginandregister.databinding.FragmentKeranjangBinding
import com.example.loginandregister.databinding.FragmentProfileBinding
import com.example.loginandregister.models.KeranjangModel
import com.example.loginandregister.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var userz: UserModel

    private var userDatabaseReference = Firebase.firestore.collection("users")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        auth = FirebaseAuth.getInstance()
        userz = UserModel()
        getUserInfo()
        //Toast.makeText(requireContext(), userz.email, Toast.LENGTH_SHORT).show()



        binding.profileEditProfil.setOnClickListener {
            Toast.makeText(requireActivity() , "Fitur ini belum tersedia" , Toast.LENGTH_SHORT).show()
        }

        binding.profileBantuan.setOnClickListener {
            Toast.makeText(requireActivity() , "Fitur ini belum tersedia" , Toast.LENGTH_SHORT).show()
        }

        binding.profileSecurity.setOnClickListener {
            Toast.makeText(requireActivity() , "Fitur ini belum tersedia" , Toast.LENGTH_SHORT).show()
        }

        binding.profileLogout.setOnClickListener{
            auth.signOut()
            //For Activity
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            Toast.makeText(requireActivity() , "Selamat kamu berhasil Log Out!" , Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }

    private fun getUserInfo() {
        userDatabaseReference
            .whereEqualTo("email", auth.currentUser!!.email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (item in querySnapshot) {
                    val users = item.toObject<UserModel>()
                    userz = users

                    binding.profileFullname.setText(userz.full_name)
                    binding.profileEmail.setText(userz.email.toString())
                }

            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_SHORT).show()
            }
    }

}