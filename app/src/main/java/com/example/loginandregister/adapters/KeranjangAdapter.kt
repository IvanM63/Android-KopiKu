package com.example.loginandregister.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginandregister.SwipeToDelete
import com.example.loginandregister.databinding.RvCartBinding
import com.example.loginandregister.models.KeranjangModel

class KeranjangAdapter(
    private val context : Context,
    private val list:ArrayList<KeranjangModel>,
    private val onLongClickRemove: OnLongClickRemove
): RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:RvCartBinding):RecyclerView.ViewHolder(binding.root){

        private val onSwipeDelete = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                list.removeAt(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangAdapter.ViewHolder {
        return ViewHolder(RvCartBinding.inflate(LayoutInflater.from(parent.context) , parent , false))
    }

    override fun onBindViewHolder(holder: KeranjangAdapter.ViewHolder, position: Int) {
        val currentItem = list[position]

        Glide
            .with(context)
            .load(currentItem.img_url)
            .into(holder.binding.cartImage)


        holder.binding.cartJudul.text = currentItem.name
        holder.binding.cartHarga.text = "Rp ${currentItem.price}"
        holder.binding.cartJmlh.text = currentItem.quantity.toString()

        var count = holder.binding.cartJmlh.text.toString().toInt()

        holder.itemView.setOnLongClickListener {
            onLongClickRemove.onLongRemove(currentItem , position)
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnLongClickRemove{
        fun onLongRemove(item:KeranjangModel , position: Int)
    }
}