package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.GetSurvey
import com.example.zohosurvey.util.SharedPreferencesManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Suppress("unchecked_cast")
class SearchViewModel(context: Context) : ViewModel() {

    fun filterByTitle(searchText: String) {
        _list.value = _list.value.filter {
            it.title?.contains(searchText.lowercase().trim() ?: " ") == true
        }
    }

    private val db = Firebase.firestore

    private val _list = MutableStateFlow<List<GetSurvey>>(emptyList())
    val list: StateFlow<List<GetSurvey>> = _list

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val preferencesManager = SharedPreferencesManager(context)
    private val username = preferencesManager.getUser()

    init {
        println("detail init")
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
    }
}