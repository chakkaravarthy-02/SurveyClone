package com.example.zohosurvey

import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val focusRequester = remember {
        FocusRequester()
    }
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        focusRequester.requestFocus()
    }
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFE5B54),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White
            ),
            title = {
                TextField(
                    modifier = Modifier.fillMaxWidth().focusRequester(focusRequester),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedTrailingIconColor = Color.White,
                        focusedTrailingIconColor = Color.White,
                        cursorColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedPlaceholderColor = Color.LightGray
                    ),
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                    },
                    trailingIcon = {
                        if(searchText.isNotEmpty()) {
                            IconButton(onClick = {
                                searchText = ""
                            }) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear")
                            }
                        }
                    },
                    placeholder = {
                        Text(fontSize = 17.sp, text = "Search a survey by its name")
                    },
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("MainScreen") {
                        popUpTo("MainScreen") {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                }
            }
        )
        Box (modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){
            //TODO("get data from database")
        }
    }
}

@Preview
@Composable
private fun SearchPreview() {
    SearchScreen(navController = rememberNavController())
}