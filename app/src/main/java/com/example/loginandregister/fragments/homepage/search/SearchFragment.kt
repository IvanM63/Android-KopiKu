package com.example.loginandregister.fragments.homepage.search

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginandregister.R
import com.example.loginandregister.adapters.ItemsShowAdapter
import com.example.loginandregister.adapters.ProductOnClickInterface
import com.example.loginandregister.adapters.SearchAdapter
import com.example.loginandregister.databinding.FragmentSearchBinding
import com.example.loginandregister.fragments.homepage.HomeFragmentDirections
import com.example.loginandregister.models.ItemsModel
import com.example.loginandregister.models.KeranjangModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.toObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable

import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment(R.layout.fragment_search), ProductOnClickInterface {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var inputMethodManger: InputMethodManager
    private lateinit var databaseReference: DatabaseReference
    private lateinit var itemList: ArrayList<ItemsModel>
    private lateinit var searchAdapter: SearchAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var disposable: Disposable


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

        //mengvisible navbar
        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.VISIBLE

        //inisiasi lateinit variable
        searchAdapter = SearchAdapter(requireContext(),this)
        itemList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("products")

        binding.rvSearch.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.rvSearch.adapter = searchAdapter

        //Search Engine


        val textChangeStream = createTextChangeObservable()
            .toFlowable(BackpressureStrategy.BUFFER) // 2

        disposable = textChangeStream
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {  }
            .observeOn(Schedulers.io())
            .map { retriveProducts(it) as List<ItemsModel> }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                showResult(it)
            }

        Log.d("myTag", "This is my message")
    }

    protected fun showResult(result: List<ItemsModel>) {
        if (result.isEmpty()) {
            //Toast.makeText(requireContext(), "Tidak ditemukan", Toast.LENGTH_SHORT).show()
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

    private fun createTextChangeObservable(): Observable<String> {

        val textChangeObservable = Observable.create<String> { emitter ->

            val textWatcher = object : TextWatcher {

                override fun afterTextChanged(s: Editable?) = Unit

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

                override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    s?.toString()?.let { emitter.onNext(it) }
                }

            }

            binding.searchBox.addTextChangedListener(textWatcher)

            emitter.setCancellable {
                binding.searchBox.removeTextChangedListener(textWatcher)
            }
        }

        return textChangeObservable
            .filter { it.length >= 2 }
            .debounce(1000, TimeUnit.MILLISECONDS)

    }

    private fun retriveProducts(query: String): List<ItemsModel>{
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                itemList.clear()

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ItemsModel::class.java)
                        if (products!!.name.contains(query,ignoreCase = true)) {
                            itemList.add(products!!)
                        }

                    }

                    searchAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }

        }

        databaseReference
            .addValueEventListener(valueEvent)

        return itemList

    }

    override fun onClickProduct(item: ItemsModel) {
        val direction =SearchFragmentDirections.actionSearchFragmentToDetailItemFragment2(item.id!!)

        Navigation.findNavController(requireView())
            .navigate(direction)
    }


}