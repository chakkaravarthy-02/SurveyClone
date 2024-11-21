package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.SharedPreferencesManager
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import java.util.concurrent.TimeUnit

@Suppress("unchecked_cast")
class MainViewModel(context: Context) : ViewModel() {
    var selectedOption by mutableStateOf("All")
        private set

    private val db = Firebase.firestore

    fun updateOptionInFilter(option: String) {
        selectedOption = option
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

    var list: MutableList<GetSurvey> = mutableListOf()

    init {
        val (username, _) = preferencesManager.getUserDetails()
        if (username != null) {
            db.collection("users")
                .document(username)
                .collection("files")
                .get()
                .addOnSuccessListener {
                    for (document in it) {
                        list.add(
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
                                pages = document.get("pages") as? Int,
                                responseTime = document.getString("responseTime"),
                                answeredToOptionA = document.get("answeredToOptionA") as? Int,
                                answeredToOptionB = document.get("answeredToOptionB") as? Int,
                                answeredToOptionC = document.get("answeredToOptionC") as? Int,
                                answeredToOptionD = document.get("answeredToOptionD") as? Int,
                                visits = document.get("visits") as? Int,
                                createdAt = document.getLong("createdAt")
                            )
                        )
                    }
                }
                .addOnFailureListener {
                    println("Error in fetching")
                }
        }
    }

}

data class GetSurvey(
    var title: String?,
    var questionData: List<Map<String, String>>?,
    var createdTime: String?,
    var response: Int?,
    var status: String?,
    var isPublished: Boolean?,
    var modified: String?,
    var modifiedTime: String?,
    var completed: Int?,
    var pages: Int?,
    var responseTime: String?,
    var answeredToOptionA: Int?,
    var answeredToOptionB: Int?,
    var answeredToOptionC: Int?,
    var answeredToOptionD: Int?,
    var visits: Int?,
    var createdAt: Long?
)
