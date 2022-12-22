package com.example.loginandregister.unused

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginandregister.adapters.KeranjangAdapter
import com.example.loginandregister.adapters.PesananAdapter
import com.example.loginandregister.databinding.FragmentKeranjangBinding
import com.example.loginandregister.databinding.FragmentLoginBinding
import com.example.loginandregister.databinding.FragmentPesananBinding
import com.example.loginandregister.models.KeranjangModel
import com.example.loginandregister.models.PesananModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class PesananFragment : Fragment() {
    private lateinit var binding: FragmentPesananBinding
    private lateinit var pesananList: ArrayList<PesananModel>
    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: PesananAdapter

    private var pesananDatabaseReference = Firebase.firestore.collection("pesanan")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPesananBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPesananBinding.bind(view)
        auth = FirebaseAuth.getInstance()

        //Layout manager buat recycleview
        val layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        pesananList = ArrayList()

        retrivePesananList()
        adapter = PesananAdapter(requireContext(), pesananList)
        binding.rvPesanan.adapter = adapter
        binding.rvPesanan.layoutManager = layoutManager



    }

    private fun retrivePesananList() {
        pesananDatabaseReference
            .whereEqualTo("uid", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (item in querySnapshot) {
                    val pesanan = item.toObject<PesananModel>()

                    pesananList.add(pesanan)
                    Log.i("Ingfo Min",pesanan.uid.toString())
                    adapter.notifyDataSetChanged()

                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), it.localizedMessage!!, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}