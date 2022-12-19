package com.example.loginandregister

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.loginandregister.databinding.FragmentLoginBinding
import com.example.loginandregister.util.Resource
import com.example.loginandregister.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginButtonLogin.setOnClickListener{
                val email = loginEmail.text.toString().trim()
                val password = loginPassword.text.toString().trim()
                viewModel.login(email,password)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.login.collect {
                when(it) {
                    is Resource.Loading -> {
                        //showLoading()
                    }
                    is Resource.Success -> {
                        //Toast.makeText()
                        Intent(requireActivity(), HomepageActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        Log.d("Test", it.data.toString())
                        //hideLoading()
                    }
                    is Resource.Error -> {
                        //hideLoading()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    } else -> Unit
                }
            }
        }
    }

}