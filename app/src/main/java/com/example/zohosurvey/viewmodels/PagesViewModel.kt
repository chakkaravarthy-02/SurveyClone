package com.example.zohosurvey.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.zohosurvey.util.encode
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PagesViewModel : ViewModel() {
    private var _questions = mutableStateListOf<Question>()
    val questions: List<Question> get() = _questions

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

    fun savePagesForUser(emailId: String, title: String) {
        val db = FirebaseFirestore.getInstance()

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

        val fileData = mapOf(
            "title" to title,
            "pages" to questionData,
            "createdTime" to formattedDate,
            "response" to 0,
            "status" to "active",
            "isPublished" to false,
            "modified" to formattedDate,
            "completed" to 0,
            "pages" to _questions.size,
            "responseTime" to formattedDate,
            "answeredToOptionA" to 0,
            "answeredToOptionB" to 0,
            "answeredToOptionC" to 0,
            "answeredToOptionD" to 0,
            "visits" to 0,
            "modifiedTime" to formattedTime,
            "createdAt" to System.currentTimeMillis()
        )

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


