package com.example.loginandregister.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginandregister.databinding.AllItemBinding
import com.example.loginandregister.models.ItemsModel

class ItemsShowAdapter(
    private val context: Context,
    private val list: List<ItemsModel>,
    private val productClickInterface: ProductOnClickInterface,

    ) : RecyclerView.Adapter<ItemsShowAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: AllItemBinding) :
            RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AllItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val image = item.img_url

        holder.binding.apply {
            Glide.with(context).load(image).into(holder.binding.allImage)
            allJudul.text = item.name
            allPrice.text = item.price
        }

        holder.itemView.setOnClickListener {
            productClickInterface.onClickProduct(list[position])
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}

interface ProductOnClickInterface {
    fun onClickProduct(item: ItemsModel)
}
