package com.example.loginandregister.fragments.homepage.search

import android.content.Context
import android.util.Log
import com.example.loginandregister.models.ItemsModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SearchEngine(private val context: Context) {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var itemList: ArrayList<ItemsModel>

    fun search(query: String): List<ItemsModel>? {
        databaseReference = FirebaseDatabase.getInstance().getReference("products")
        Thread.sleep(2000)
        Log.d("Searching", "Searching for $query")
        /*databaseReference.startAt(query).get().addOnCompleteListener {
            if (it.isSuccessful) {
                itemList.add(it.)
            }
        }*/
        val item1 = ItemsModel("56","ARAB","","","","","")
        val item2 = ItemsModel("69","ARAB2","","","","","")
        itemList.add(item1)
        itemList.add(item2)
        return itemList
        //return CheeseDatabase.getInstance(context).cheeseDao().findCheese("%$query%")
    }
}