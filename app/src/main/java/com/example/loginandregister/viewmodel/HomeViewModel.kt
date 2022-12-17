package com.example.loginandregister.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginandregister.models.ItemsModel
import com.example.loginandregister.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
): ViewModel() {

    private val _popularItems = MutableStateFlow<Resource<List<ItemsModel>>>(Resource.Unspecified())
    val popularItem: StateFlow<Resource<List<ItemsModel>>> = _popularItems

    init {
        fetchPopularItems()
    }

    fun fetchPopularItems() {
        viewModelScope.launch {
            _popularItems.emit(Resource.Loading())
        }

        firestore.collection("Items")
            .whereEqualTo("category","popular").get().addOnSuccessListener { result ->
            val popularItemsList = result.toObjects(ItemsModel::class.java)
                viewModelScope.launch {
                    _popularItems.emit(Resource.Success(popularItemsList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _popularItems.emit(Resource.Error(it.toString()))
                }
            }
    }
}