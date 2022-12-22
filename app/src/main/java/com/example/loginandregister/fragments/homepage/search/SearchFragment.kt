package com.example.loginandregister.fragments.homepage.search

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.loginandregister.R
import com.example.loginandregister.adapters.ItemsShowAdapter
import com.example.loginandregister.adapters.SearchAdapter
import com.example.loginandregister.databinding.FragmentSearchBinding
import com.example.loginandregister.models.ItemsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var inputMethodManger: InputMethodManager
    private lateinit var databaseReference: DatabaseReference
    private lateinit var itemList: ArrayList<ItemsModel>
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var auth: FirebaseAuth

    protected lateinit var searchEngine: SearchEngine


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        showKeyboardAutomatically()
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        //Hide Navigation
        /*val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.GONE*/



        searchAdapter = SearchAdapter()
        binding.rvSearch.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.rvSearch.adapter = searchAdapter

        searchEngine = SearchEngine(requireContext())


        /*val initialLoadDisposable = loadInitialData(requireContext()).subscribe()
        compositeDisposable.add(initialLoadDisposable)*/


    }

    protected fun showResult(result: List<ItemsModel>) {
        if (result.isEmpty()) {
            Toast.makeText(requireContext(), "Tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
        searchAdapter.items = result
    }

    private fun showKeyboardAutomatically() {
        inputMethodManger =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManger.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )

        binding.searchBox.requestFocus()
    }




}