package com.example.loginandregister.unused

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginandregister.R
import com.example.loginandregister.adapters.KeranjangAdapter
import com.example.loginandregister.databinding.FragmentKeranjangBinding
import com.example.loginandregister.databinding.FragmentLoginBinding
import com.example.loginandregister.models.KeranjangModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class KeranjangFragment : Fragment(), KeranjangAdapter.OnLongClickRemove {
    private lateinit var binding: FragmentKeranjangBinding
    private lateinit var cartList: ArrayList<KeranjangModel>
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: KeranjangAdapter
    private var subTotalPrice = 0
    private var totalPrice = 0

    private var orderDatabaseReference = Firebase.firestore.collection("orders")
    private var pesananDatabaseReference = Firebase.firestore.collection("pesanan")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeranjangBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentKeranjangBinding.bind(view)
        auth = FirebaseAuth.getInstance()


        //Nav Bar jadi keliatan lagi
        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.VISIBLE
        //------------------------//

        /*binding.cartActualToolbar.setNavigationOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }*/


        val layoutManager = LinearLayoutManager(context)
        cartList = ArrayList()

        retrieveCartItems()

        adapter = KeranjangAdapter(requireContext(),cartList ,this)
        binding.rvCarts.adapter = adapter
        binding.rvCarts.layoutManager = layoutManager

        binding.keranjangCheckout.setOnClickListener {
            if (cartList.isEmpty()) {
                Toast.makeText(requireActivity(),"Keranjang kamu kosong nih!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireActivity(),"Wah! kamu baru saja memesan dengan total Rp ${subTotalPrice}", Toast.LENGTH_LONG).show()
                cartList.clear()
                binding.keranjangSubtotal.text ="0"
                // TODO: remove the data of the Products from the fireStore after checkout or insert a boolean isDelivered
                pesananDatabaseReference.whereEqualTo("uid",auth.currentUser!!.uid).get().addOnCompleteListener {productToDelete ->

                }
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun retrieveCartItems() {

        orderDatabaseReference
            .whereEqualTo("uid",auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (item in querySnapshot) {
                    val cartProduct = item.toObject<KeranjangModel>()

                    cartList.add(cartProduct)
                    subTotalPrice += cartProduct.price!!.toInt()
                    totalPrice += cartProduct.price!!.toInt()

                    binding.keranjangSubtotal.text = "Rp ${subTotalPrice.toString()}"
                    //binding.tvLastTotalPrice.text = totalPrice.toString()
                    //binding.tvLastSubTotalItems.text = "SubTotal Items(${cartList.size})"
                    adapter.notifyDataSetChanged()


                }

            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_SHORT).show()
            }


    }

    override fun onLongRemove(item: KeranjangModel , position:Int) {


        orderDatabaseReference
            .whereEqualTo("uid",item.uid)
            .whereEqualTo("pid",item.pid)
            .get()
            .addOnSuccessListener { querySnapshot ->

                for (item in querySnapshot){
                    orderDatabaseReference.document(item.id).delete()
                    cartList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                    Toast.makeText(requireContext(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show()
                }

            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Gagal Menghapus Barang", Toast.LENGTH_SHORT).show()
            }

    }
}