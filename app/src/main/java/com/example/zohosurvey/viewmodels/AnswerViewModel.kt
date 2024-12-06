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
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Suppress("unchecked_cast")
class AnswerViewModel(context: Context): ViewModel() {

    fun filterList(list: List<GetSurvey>, link: String?): GetSurvey?{
        return list.find { URLDecoder.decode(it.link, StandardCharsets.UTF_8.toString()) == link }
    }

    fun setListToNull(pages: Long) {
        for(i in 0..<pages){
            _options.value += ""
        }
    }

    fun setOption(currentPage: Int, str: String) {
        val updatedOptions = _options.value.toMutableList()
        updatedOptions[currentPage] = str
        _options.value = updatedOptions
    }

    fun updateAnswers(survey: GetSurvey?, value: List<String>) {
        var index = 0
        var response = 0
        if(survey?.response!=null){
            response = survey.response ?: 0
        }
        response++
        val answerMap = survey?.answerData
        answerMap?.forEach { map ->
            map.forEach { (key, currentValue) ->
                if (value[index]==key) {
                    map[key] = currentValue + 1
                }
            }
            index++
        }
        val updatedAnswer = mapOf(
            "answerData" to answerMap,
            "response" to response
        )

        scope.launch{
            survey?.title?.let {
                db.collection("users")
                    .document(user.toString())
                    .collection("files")
                    .document(it.encode())
                    .update(updatedAnswer)
                    .addOnSuccessListener {
                        println("answers updated")
                    }
                    .addOnFailureListener {
                        println("answers failed")
                    }
            }
        }
    }

    private val _options = MutableStateFlow<List<String>>(emptyList())
    val options: StateFlow<List<String>> = _options

    private val preferencesManager = SharedPreferencesManager(context)
    private val user = preferencesManager.getUser()

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    private val db = Firebase.firestore

    private val _questions = MutableStateFlow<List<GetSurvey>>(emptyList())
    val questions: StateFlow<List<GetSurvey>> = _questions

    init{
        scope.launch{
            try{
                if (user != null) {
                    val documents = db.collection("users")
                        .document(user)
                        .collection("files")
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
                    _questions.value += getSurvey
                }
            } catch (e: Exception){
                println("error in fetching files")
            }
        }
    }
}