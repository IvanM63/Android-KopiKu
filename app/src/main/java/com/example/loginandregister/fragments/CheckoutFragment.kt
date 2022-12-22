package com.example.loginandregister.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginandregister.R
import com.example.loginandregister.databinding.FragmentCheckoutBinding
import com.example.loginandregister.databinding.FragmentKeranjangBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CheckoutFragment : Fragment() {
    private lateinit var binding: FragmentCheckoutBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //HIDE NAV BOTTOM
        val bottomNavigation =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigationView)
        bottomNavigation.visibility = View.GONE

        /*binding.keranjangCheckout.setOnClickListener {
            if (cartList.isEmpty()) {
                Toast.makeText(requireActivity(),"Keranjang kamu kosong nih!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireActivity(),"Wah! kamu baru saja memesan dengan total Rp ${subTotalPrice}", Toast.LENGTH_LONG).show()

                binding.keranjangSubtotal.text ="0"
                // TODO: remove the data of the Products from the fireStore after checkout or insert a boolean isDelivered

                deleteCart(cartList[0])
                addToPesanan(cartList[0])

                cartList.clear()
                adapter.notifyDataSetChanged()
            }
        }*/
    }


}