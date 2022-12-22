package com.example.loginandregister.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginandregister.models.ItemsModel
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.rv_search.view.*

class SearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val cheese = items[position]
        holder.itemView.search_judul.text = cheese.name

    }

}