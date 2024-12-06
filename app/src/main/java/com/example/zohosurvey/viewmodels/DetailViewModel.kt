package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.GetSurvey
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
import kotlinx.coroutines.withContext

@Suppress("unchecked_cast")
class DetailViewModel(context: Context) : ViewModel() {

    private val db = Firebase.firestore

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val preferencesManager = SharedPreferencesManager(context)
    private val username = preferencesManager.getUser()

    fun deleteSurvey(title: String) {
        scope.launch{
            db.collection("users")
                .document(preferencesManager.getUser().toString())
                .collection("files")
                .document(title.encode())
                .delete()
                .addOnSuccessListener {
                    println("done")
                }
                .addOnFailureListener {
                    println("something problem while deleting...")
                }
        }
    }

    fun publishThisSurvey(title: String) {
        scope.launch {
            val updated = mapOf(
                "isPublished" to true
            )
            db.collection("users")
                .document(username.toString())
                .collection("files")
                .document(title.encode())
                .update(updated)
                .addOnSuccessListener {
                    println("Publish Updated")
                }
                .addOnFailureListener {
                    println("Update Failed")
                }
        }
    }

    fun changeStatus(title: String, status: String) {
        scope.launch {
            val updated = mapOf(
                "status" to status
            )
            db.collection("users")
                .document(username.toString())
                .collection("files")
                .document(title.encode())
                .update(updated)
                .addOnSuccessListener {
                    println("Status updated")
                }
                .addOnFailureListener {
                    println("Status failed")
                }
        }
    }

    private val _list = MutableStateFlow<List<GetSurvey>>(emptyList())
    val list: StateFlow<List<GetSurvey>> = _list

    init {
        println("detail init")
        if (_list.value.isEmpty()) {
            scope.launch {
                if (username != null) {
                    try {
                        val documents = db.collection("users")
                            .document(username)
                            .collection("files")
                            .orderBy(
                                "createdAt",
                                com.google.firebase.firestore.Query.Direction.ASCENDING
                            )
                            .get()
                            .await()
                        val getSurvey = documents.map { document ->
                            GetSurvey(
                                title = document.getString("title"),
                                questionData = document.get("questionData") as? List<Map<String, String>>,
                                createdTime = document.getString("createdTime"),
                                response = document.get("response") as? Int,
                                status = document.getString("status"),
                                isPublished = document.getBoolean("isPublished"),
                                modified = document.getString("modified"),
                                modifiedTime = document.getString("modifiedTime"),
                                completed = document.get("completed") as? Int,
                                pages = document.getLong("pages"),
                                responseTime = document.getString("responseTime"),
                                answerData = document.get("answerData") as? List<MutableMap<String, Int>>,
                                visits = document.get("visits") as? Int,
                                createdAt = document.getLong("createdAt"),
                                link = document.getString("link")
                            )
                        }
                        _list.value += getSurvey
                    } catch (e: Exception) {
                        println("Error in fetching $e")
                    }
                }
            }
        } else {
            println("in main screen list is not empty")
        }
    }
}