package com.example.loginandregister.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandregister.databinding.AllItemBinding
import com.example.loginandregister.models.ItemsModel

class AllItemsAdapter
    : RecyclerView.Adapter<AllItemsAdapter.AllItemsAdapterViewHolder>() {

    inner class AllItemsAdapterViewHolder(private val binding: AllItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsModel) {
            binding.apply {
                //Glide.with(itemView).load(item.img_url).into(popImage)
                allJudul.text = item.name
                allPrice.text = item.price
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllItemsAdapterViewHolder {
        return AllItemsAdapterViewHolder(
            AllItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: AllItemsAdapterViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}