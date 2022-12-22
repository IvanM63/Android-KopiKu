package com.example.loginandregister.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginandregister.R
import com.example.loginandregister.adapters.JudulOnlyAdapter
import com.example.loginandregister.adapters.KeranjangAdapter
import com.example.loginandregister.databinding.FragmentCheckoutBinding
import com.example.loginandregister.databinding.FragmentKeranjangBinding
import com.example.loginandregister.models.KeranjangModel
import com.example.loginandregister.models.PesananModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var cartList: ArrayList<KeranjangModel>
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: JudulOnlyAdapter
    private var subTotalPrice = 10000
    private var totalPrice = 0

    private var orderDatabaseReference = Firebase.firestore.collection("orders")
    private var pesananDatabaseReference = Firebase.firestore.collection("pesanan")

    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckoutBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        //HIDE NAV BOTTOM
        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.GONE

        binding.checkoutBackButton.setNavigationOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }

        val layoutManager = LinearLayoutManager(context)
        cartList = ArrayList()

        retrieveCartItems()

        adapter = JudulOnlyAdapter(requireContext(),cartList)
        binding.rvJudul.adapter = adapter
        binding.rvJudul.layoutManager = layoutManager


        builder = AlertDialog.Builder(requireContext())
        binding.kotakSend.setOnClickListener {
            //alert code
            builder.setTitle("Pengiriman")
                .setMessage("Saat ini kami hanya menerima pengiriman melalui GoSend sahaja")
                .setPositiveButton("OK") {dialogInterface,it ->
                    dialogInterface.cancel()
                }.show()
            //Toast.makeText(requireActivity(),"Saat ini kami hanya menerima pengiriman GoSend", Toast.LENGTH_LONG).show()
        }

        binding.checkoutBuatPesanan.setOnClickListener {
            Toast.makeText(requireActivity(),"Wah! kamu baru saja memesan dengan total Rp ${subTotalPrice}", Toast.LENGTH_LONG).show()

            binding.keranjangSubtotal.text ="0"
            // TODO: remove the data of the Products from the fireStore after checkout or insert a boolean isDelivered

            deleteCart(cartList[0])
            addToPesanan(cartList[0])

            cartList.clear()
            adapter.notifyDataSetChanged()


            Navigation.findNavController(requireView()).popBackStack()
            Navigation.findNavController(requireView()).popBackStack()

        }
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

    private fun deleteCart(keranjang: KeranjangModel) = CoroutineScope(Dispatchers.IO).launch {
        val personQuery = orderDatabaseReference
            .whereEqualTo("uid",keranjang.uid)
            .get()
            .await()
        if(personQuery.documents.isNotEmpty()) {
            for(documents in personQuery) {
                try {
                    orderDatabaseReference.document(documents.id).delete().await()
                    Toast.makeText(requireContext(), "Berhasil Menghapus", Toast.LENGTH_SHORT).show()
                    /*pesananDatabaseReference.document(documents.id).update(mapOf(
                        "uid" to FieldValue.delete()
                    ))*/
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        //Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "Cart tidak ditemukan di database", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToPesanan(keranjang: KeranjangModel) {
        val pesanan = PesananModel(keranjang.uid, "Sedang Dikirim",subTotalPrice.toString())
        pesananDatabaseReference.add(pesanan).addOnCompleteListener{task ->
            if(task.isSuccessful){
                //Toast.makeText(requireContext(), "Berhasil Menambahkan ke Pesanan", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), task.exception!!.localizedMessage!!, Toast.LENGTH_SHORT).show()
            }
        }
    }


}