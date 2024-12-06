package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import java.util.concurrent.TimeUnit

@Suppress("unchecked_cast")
class MainViewModel(context: Context) : ViewModel() {
    var selectedOption by mutableStateOf("All")
        private set

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val db = Firebase.firestore

    fun updateOptionInFilter(option: String) {
        selectedOption = option

        if (option == "All") {
            _list.value = _tempList.value
        }
        if (option == "Published") {
            _list.value = _tempList.value.filter { it.isPublished == true }
        }
        if (option == "Draft") {
            _list.value = _tempList.value.filter { it.isPublished == false }
        }
        if (option == "Closed") {
            _list.value = _tempList.value.filter { it.status == "Closed" }
        }
    }

    private val _isLogin = mutableStateOf(false)
    val isLogin: MutableState<Boolean> get() = _isLogin

    private val preferencesManager = SharedPreferencesManager(context)

    fun checkUserLoggedIn() {
        val (username, lastLogin) = preferencesManager.getUserDetails()

        _isLogin.value =
            username != null && lastLogin != 0L && System.currentTimeMillis() - lastLogin < TimeUnit.HOURS.toMillis(
                24
            )
    }

    private val _list = MutableStateFlow<List<GetSurvey>>(emptyList())
    val list: StateFlow<List<GetSurvey>> = _list

    private val _tempList = MutableStateFlow<List<GetSurvey>>(emptyList())

    init {
        scope.launch {
            val username = preferencesManager.getUser()
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
                    _tempList.value = _list.value
                } catch (e: Exception) {
                    println("Error in fetching $e")
                }
            }
        }
    }
}
