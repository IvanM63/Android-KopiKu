package com.example.loginandregister.viewmodel

import androidx.lifecycle.ViewModel
import com.example.loginandregister.models.UserModel
import com.example.loginandregister.util.Constant.USER_COLLECTION
import com.example.loginandregister.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.context.AttributeContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
        ):ViewModel(){

        private val _register = MutableStateFlow<Resource<UserModel>>(Resource.Unspecified())
        val register: MutableStateFlow<Resource<UserModel>> = _register

            fun createAccountWithEmailAndPassword(user: UserModel) {
                firebaseAuth.createUserWithEmailAndPassword(user.email,user.password)
                    .addOnSuccessListener {
                        it.user?.let {
                            saveUserInfo(it.uid, user)

                        }
                    }.addOnFailureListener{
                            _register.value = Resource.Error(it.message.toString())
                    }
            }

    private fun saveUserInfo(userUid: String, user: UserModel) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }
}