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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginandregister.adapters.PopularAdapter
import com.example.loginandregister.databinding.FragmentHomeBinding
import com.example.loginandregister.util.Resource
import com.example.loginandregister.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

private val TAG = "HomeFragment"
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var popularItemAdapter: PopularAdapter
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(),"aaa", Toast.LENGTH_SHORT).show()
        setupPopularAdapterRV()
        lifecycleScope.launchWhenCreated {
            viewModel.popularItem.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        //showLoading()
                    }
                    is Resource.Success -> {
                        popularItemAdapter.differ.submitList(it.data)
                        //hideLoading()
                    }
                    is Resource.Error -> {
                        //hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun hideLoading() {
        TODO("Not yet implemented")
    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    private fun setupPopularAdapterRV() {
        popularItemAdapter = PopularAdapter()
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

}