package com.example.zohosurvey.util

fun String.encode(): String{
    val str = this
    var encoded = ""
    for(i in str.indices){
        encoded+=str[i]+1
    }
    return encoded
}