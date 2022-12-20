package com.example.loginandregister.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginandregister.databinding.PopularItemBinding
import com.example.loginandregister.models.ItemsModel

class ItemsPopularAdapter(
    private val context: Context,
    private val list: List<ItemsModel>,
) : RecyclerView.Adapter<ItemsPopularAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PopularItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val image = item.img_url

        holder.binding.apply {
            Glide.with(context).load(image).into(holder.binding.popImage)
            popJudul.text = item.name
            popPrice.text = "Rp ${item.price}"
        }

        /*holder.itemView.setOnClickListener {
            productClickInterface.onClickProduct(list[position])
        }*/

    }

    override fun getItemCount(): Int {
        return list.size
    }

}
