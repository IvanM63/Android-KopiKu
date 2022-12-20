package com.example.loginandregister.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginandregister.databinding.RvCartBinding
import com.example.loginandregister.databinding.RvPesananBinding
import com.example.loginandregister.models.PesananModel

class PesananAdapter(
    private val context : Context,
    private val list:ArrayList<PesananModel>,
): RecyclerView.Adapter<PesananAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvPesananBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananAdapter.ViewHolder {
        return ViewHolder(RvPesananBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: PesananAdapter.ViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.pesananJudul.text = "Pesanan ${position+1}"
        holder.binding.pesananStatus.text = "Status : ${currentItem.status}"

    }

    override fun getItemCount(): Int {
        return list.size
    }

}