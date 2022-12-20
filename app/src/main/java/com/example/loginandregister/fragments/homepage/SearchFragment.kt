package com.example.loginandregister.fragments.homepage

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import com.example.loginandregister.R
import com.example.loginandregister.adapters.ItemsShowAdapter
import com.example.loginandregister.databinding.FragmentDetailItemBinding
import com.example.loginandregister.databinding.FragmentHomeBinding
import com.example.loginandregister.databinding.FragmentSearchBinding
import com.example.loginandregister.models.ItemsModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import io.reactivex.rxjava3.core.Observable

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var inputMethodManger: InputMethodManager
    private lateinit var databaseReference: DatabaseReference
    private lateinit var itemList: ArrayList<ItemsModel>
    private lateinit var itemAdapter: ItemsShowAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        //Hide Navigation
        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.GONE



        showKeyboardAutomatically()

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

    private fun searchItems() {
        Observable.create<String> {emitter ->


        }
    }

}