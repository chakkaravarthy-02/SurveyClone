package com.example.zohosurvey.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var selectedOption by mutableStateOf("All")
        private set

    fun updateOptionInFilter(option: String) {
        selectedOption = option
    }

    val list: List<String> = listOf()
}
