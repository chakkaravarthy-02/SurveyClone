package com.example.zohosurvey.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.SharedPreferencesManager
import com.example.zohosurvey.util.encode
import com.google.firebase.firestore.FirebaseFirestore
import java.net.URLEncoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PagesViewModel(context: Context): ViewModel() {
    private var _questions = mutableStateListOf<Question>()
    val questions: List<Question> get() = _questions

    private val preferencesManager = SharedPreferencesManager(context)
    private val db = FirebaseFirestore.getInstance()

    private val currentDateTime = LocalDateTime.now()
    private val formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    private val formatterForTime = DateTimeFormatter.ofPattern("HH:MM:SS")
    private val formattedDate = currentDateTime.format(formatter)
    private val formattedTime = currentDateTime.format(formatterForTime)

    init {
        _questions.add(Question("", "", "", "", ""))
    }

    fun setQuestionForPage(pageId: Int, question: String) {
        _questions[pageId].questionText = question
    }

    fun setOption1(pageId: Int, optionA: String) {
        _questions[pageId].optionA = optionA
    }

    fun setOption2(pageId: Int, optionB: String) {
        _questions[pageId].optionB = optionB
    }

    fun setOption3(pageId: Int, optionC: String) {
        _questions[pageId].optionC = optionC
    }

    fun setOption4(pageId: Int, optionD: String) {
        _questions[pageId].optionD = optionD
    }

    fun addQuestion() {
        _questions.add(Question("", "", "", "", ""))
    }

    fun savePagesForUser(title: String) {
        val emailId = preferencesManager.getUser()
        val questionData = _questions.map { question ->
            mapOf(
                "questionText" to question.questionText,
                "optionA" to question.optionA,
                "optionB" to question.optionB,
                "optionC" to question.optionC,
                "optionD" to question.optionD,
            )
        }

        val fileId = title.encode()

        val link = generateLink(fileId)

        val fileData = mapOf(
            "title" to title,
            "questionData" to questionData,
            "createdTime" to formattedDate,
            "response" to 0,
            "status" to "Active",
            "isPublished" to false,
            "modified" to formattedDate,
            "completed" to 0,
            "pages" to questionData.size,
            "responseTime" to formattedDate,
            "answeredToOptionA" to 0,
            "answeredToOptionB" to 0,
            "answeredToOptionC" to 0,
            "answeredToOptionD" to 0,
            "visits" to 0,
            "modifiedTime" to formattedTime,
            "createdAt" to System.currentTimeMillis(),
            "link" to link
        )

        if (emailId != null) {
            db.collection("users")
                .document(emailId)
                .collection("files")
                .document(fileId)
                .set(fileData)
                .addOnSuccessListener {
                    println("Pages saved under file $fileId for user $emailId!")
                }
                .addOnFailureListener { e ->
                    println("Error saving pages: ${e.message}")
                }
        }
    }

    private fun generateLink(fileId: String): String? {
        var encoded = fileId.encode()
        encoded = URLEncoder.encode(encoded,"UTF-8")
        val currentTime = System.currentTimeMillis().toString()
        val deepLink = "https://surveyz.com/$encoded-$currentTime"
        return deepLink
    }
}

data class Question(
    var initialQuestionText: String = "",
    var initialOptionA: String = "",
    var initialOptionB: String = "",
    var initialOptionC: String = "",
    var initialOptionD: String = ""
) {
    var questionText by mutableStateOf(initialQuestionText)
    var optionA by mutableStateOf(initialOptionA)
    var optionB by mutableStateOf(initialOptionB)
    var optionC by mutableStateOf(initialOptionC)
    var optionD by mutableStateOf(initialOptionD)
}


