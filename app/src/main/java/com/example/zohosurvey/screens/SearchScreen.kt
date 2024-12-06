package com.example.zohosurvey.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.viewmodelfactorys.SearchFactory
import com.example.zohosurvey.viewmodels.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    searchViewModel: SearchViewModel = viewModel(
        factory = SearchFactory(
            LocalContext.current
        )
    )
) {
    val list = searchViewModel.list.collectAsState()
    val focusRequester = remember {
        FocusRequester()
    }
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
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
                        if (searchText.isNotEmpty()) {
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
            },
            actions = {
                IconButton(onClick = {
                    searchViewModel.filterByTitle(searchText)
                }) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                }
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            if(list.value.isNotEmpty()){
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    val temp = list.value
                    itemsIndexed(temp) { index, item ->
                        ListRow(
                            list = temp,
                            index = index,
                            getSurvey = item,
                            navController = navController
                        )
                        HorizontalLine()
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun SearchPreview() {
    SearchScreen(navController = rememberNavController())
}