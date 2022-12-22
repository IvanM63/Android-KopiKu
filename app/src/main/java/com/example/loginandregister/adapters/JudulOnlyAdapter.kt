package com.example.loginandregister.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandregister.databinding.RvCartBinding
import com.example.loginandregister.databinding.RvJudulOnlyBinding
import com.example.loginandregister.models.KeranjangModel

class JudulOnlyAdapter(
    private val context : Context,
    private val list:ArrayList<KeranjangModel>,
): RecyclerView.Adapter<JudulOnlyAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvJudulOnlyBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JudulOnlyAdapter.ViewHolder {
        return ViewHolder(RvJudulOnlyBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: JudulOnlyAdapter.ViewHolder, position: Int) {
        val currentItem = list[position]

        holder.binding.judulJudul.text = currentItem.name

    }

    override fun getItemCount(): Int {
        return list.size
    }

}