package com.example.loginandregister.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandregister.databinding.PopularItemBinding
import com.example.loginandregister.models.ItemsModel

class PopularAdapter
    : RecyclerView.Adapter<PopularAdapter.PopularAdapterViewHolder>() {

    inner class PopularAdapterViewHolder(private val binding: PopularItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: ItemsModel) {
                binding.apply {
                    //Glide.with(itemView).load(item.img_url).into(popImage)
                    popJudul.text = item.name
                }
            }
        }

    private val diffCallBack = object : DiffUtil.ItemCallback<ItemsModel>(){
        override fun areItemsTheSame(oldItem: ItemsModel, newItem: ItemsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemsModel, newItem: ItemsModel): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdapterViewHolder {
        return PopularAdapterViewHolder(
            PopularItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularAdapterViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}