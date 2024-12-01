package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.SharedPreferencesManager
import com.example.zohosurvey.util.encode
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LinkViewModel(context: Context) : ViewModel() {

    private val _link = MutableStateFlow("")
    val link: StateFlow<String> = _link

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun getLink(title: String?) {
        println(title)
        scope.launch {
            try {
                if (user != null) {
                    if(title != null){
                        val documentSnapshot = db.collection("users")
                            .document(user)
                            .collection("files")
                            .document(title.encode())
                            .get()
                            .await()

                        if (documentSnapshot.exists()) {
                            _link.value += documentSnapshot.getString("link").toString()
                        } else {
                            println("Document does not exist.")
                        }
                    }
                }
            }catch(e: Exception) {
                println("Error retrieving document: ${e.message}")
            }
        }
    }

    private val preferencesManager = SharedPreferencesManager(context)

    private val user = preferencesManager.getUser()

    private val db = Firebase.firestore


}