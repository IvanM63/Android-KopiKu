package com.example.loginandregister.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginandregister.R
import com.example.loginandregister.databinding.PopularItemBinding
import com.example.loginandregister.databinding.RvSearchBinding
import com.example.loginandregister.models.ItemsModel
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.rv_search.view.*

class SearchAdapter(
    private val context : Context,
    private val productClickInterface: com.example.loginandregister.adapters.ProductOnClickInterface,

    ): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var compositeDisposable = CompositeDisposable()

    var items: List<ItemsModel> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        compositeDisposable.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        return ViewHolder(
            RvSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    inner class ViewHolder(val binding: RvSearchBinding) :
        RecyclerView.ViewHolder(binding.root)



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cheese = items[position]

        Glide
            .with(context)
            .load(cheese.img_url)
            .into(holder.itemView.search_image)
        holder.itemView.search_judul.text = cheese.name

        holder.itemView.setOnClickListener {
            productClickInterface.onClickProduct(items[position])
        }

    }

    interface ProductOnClickInterface {
        fun onClickProduct(item: ItemsModel)
    }

}