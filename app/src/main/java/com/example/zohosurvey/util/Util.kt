package com.example.zohosurvey.util

fun String.encode(): String {
    val str = this
    var encoded = ""
    for (i in str.indices) {
        encoded += str[i] + 1
    }
    return encoded
}

fun String.decode(): String {
    val str = this
    var decoded = ""
    for (i in str.indices) {
        decoded += str[i] - 1
    }
    return decoded
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
    var pages: Long?,
    var responseTime: String?,
    var answerData: List<MutableMap<String, Int>>?,
    var visits: Int?,
    var createdAt: Long?,
    var link: String?
)