package com.example.loginandregister.unused

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginandregister.databinding.FragmentKeranjangBinding
import com.example.loginandregister.databinding.FragmentLoginBinding
import com.example.loginandregister.databinding.FragmentPesananBinding

class PesananFragment : Fragment() {
    private lateinit var binding: FragmentPesananBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPesananBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}