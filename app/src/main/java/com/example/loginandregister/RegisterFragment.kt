package com.example.loginandregister

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.loginandregister.databinding.FragmentRegisterBinding
import com.example.loginandregister.models.UserModel
import com.example.loginandregister.util.Resource
import com.example.loginandregister.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


private val TAG = "RegisterFragment"
@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

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
        binding.apply {
            registerButtonRegister.setOnClickListener{
                val user = UserModel(
                    registerFullName.text.toString().trim(),
                    registerEmail.text.toString().trim(),
                    registerPassword.text.toString().trim()
                )
                viewModel.createAccountWithEmailAndPassword(user)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.register.collect {
                when(it) {
                    is Resource.Loading -> {
                        //showLoading()
                    }
                    is Resource.Success -> {
                        //Toast.makeText()
                        Log.d("Test", it.data.toString())
                        //hideLoading()
                    }
                    is Resource.Error -> {
                        //hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}