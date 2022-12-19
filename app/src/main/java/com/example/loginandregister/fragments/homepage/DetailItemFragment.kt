package com.example.loginandregister.fragments.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.loginandregister.R
import com.example.loginandregister.databinding.FragmentDetailItemBinding
import com.example.loginandregister.databinding.FragmentHomeBinding
import com.example.loginandregister.models.ItemOrderModel
import com.example.loginandregister.models.ItemsModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class DetailItemFragment : Fragment() {

    private lateinit var binding: FragmentDetailItemBinding
    private lateinit var productDatabaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val args: DetailItemFragmentArgs by navArgs()

    private val orderDatabaseReference = Firebase.firestore.collection("orders")

    private lateinit var currentUID :  String
    private lateinit var orderImageUrl:String
    private lateinit var orderName:String
    private var orderQuantity:Int  = 1
    private lateinit var orderPrice:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailItemBinding.bind(view)

        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.GONE

        productDatabaseReference = FirebaseDatabase.getInstance().getReference("products")

        val productId = args.productId
        auth = FirebaseAuth.getInstance()

        currentUID = auth.currentUser!!.uid

        binding.detailBackButton.setNavigationOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }


        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (dataSnapshot in snapshot.children) {
                        val products = dataSnapshot.getValue(ItemsModel::class.java)

                        if (products!!.id == productId) {
                            Glide
                                .with(requireContext())
                                .load(products.img_url)
                                .into(binding.detailImg)

                            orderImageUrl = products.img_url!!
                            orderName = products.name!!
                            orderPrice = products.price!!

                            binding.detailHarga.text = "Rp ${products.price}"
                            binding.detailJudul.text = products.name
                            binding.detailDesc.text = products.description
                        }


                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        }


        productDatabaseReference.addValueEventListener(valueEvent)

        //Add to cart Fungsi
        binding.detailButtonKeranjang.setOnClickListener {

            val orderedProduct = ItemOrderModel(currentUID,productId,orderName,orderQuantity,orderPrice,orderImageUrl)

            addDataToOrdersDatabase(orderedProduct)


        }

    }

    private fun addDataToOrdersDatabase(orderedProduct: ItemOrderModel) {
        orderDatabaseReference.add(orderedProduct).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(requireContext(), "Berhasil Menambahkan ke keranjang", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), task.exception!!.localizedMessage!!, Toast.LENGTH_SHORT).show()
            }
        }
    }


}