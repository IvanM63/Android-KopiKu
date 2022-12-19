package com.example.loginandregister.fragments.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.loginandregister.R
import com.example.loginandregister.adapters.ItemsShowAdapter
import com.example.loginandregister.adapters.ProductOnClickInterface
import com.example.loginandregister.databinding.FragmentHomeBinding
import com.example.loginandregister.models.ItemsModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment(R.layout.fragment_home), ProductOnClickInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var itemList: ArrayList<ItemsModel>
    private lateinit var itemAdapter: ItemsShowAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.VISIBLE

        itemList = ArrayList()
        databaseReference = FirebaseDatabase.getInstance().getReference("products")
        auth = FirebaseAuth.getInstance()

        //Implement Items Recycle View (yeay)
        val productLayoutManager = GridLayoutManager(context, 2)
        itemAdapter = ItemsShowAdapter(requireContext(), itemList, this)
        binding.rvAllItem.layoutManager = productLayoutManager
        binding.rvAllItem.adapter = itemAdapter
        setItemsData()

        //Testing User
        Toast.makeText(requireContext(),auth.currentUser!!.email, Toast.LENGTH_SHORT).show()
    }

    private fun setItemsData() {
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                itemList.clear()

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ItemsModel::class.java)
                        itemList.add(products!!)
                    }

                    itemAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            }

        }

        databaseReference.addValueEventListener(valueEvent)
    }

    override fun onClickProduct(item: ItemsModel) {
        val direction = HomeFragmentDirections.actionHomeFragmentToDetailItemFragment2(item.id!!)

        Navigation.findNavController(requireView())
            .navigate(direction)
    }


}