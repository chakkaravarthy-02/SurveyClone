package com.example.zohosurvey.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.util.SharedPreferencesManager
import com.example.zohosurvey.viewmodelfactorys.PagesFactory
import com.example.zohosurvey.viewmodels.PagesViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    title: String,
    pagesViewModel: PagesViewModel = viewModel(
        factory = PagesFactory(LocalContext.current)
    )
) {
    val item = mutableListOf(1)

    val questions = pagesViewModel.questions

    val pagerState = rememberPagerState()

    var totalPages by rememberSaveable {
        mutableIntStateOf(item.size)
    }


    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        )
        {
            TopAppBar(colors = TopAppBarColors(
                containerColor = Color(0xFFFE5B54),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
                scrolledContainerColor = Color(0xFFFE5B54)
            ),
                title = { Text(fontWeight = FontWeight.SemiBold, text = title) },
                actions = {
                    IconButton(onClick = {
                        item.add(item[item.size - 1] + 1)
                        pagesViewModel.addQuestion()
                        totalPages = item.size
                    }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "add page")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("AddingScreen")
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                })
            Spacer(modifier = Modifier.padding(2.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(color = Color.Black, text = "Page ")
                Text(color = Color.Black, text = (pagerState.currentPage + 1).toString())
                Text(color = Color.Black, text = "/$totalPages")
            }
            Spacer(modifier = Modifier.padding(2.dp))
            HorizontalPager(
                count = item.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) {
                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    onClick = {},
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                    ) {
                        Text(
                            text = "Enter the survey question :",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        TextField(
                            value = questions[currentPage].questionText,
                            onValueChange = { new ->
                                pagesViewModel.setQuestionForPage(currentPage, new)
                            })
                        Text(
                            text = "Enter the options:",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        TextField(value = questions[currentPage].optionA, onValueChange = { new ->
                            pagesViewModel.setOption1(currentPage, new)
                        })
                        TextField(value = questions[currentPage].optionB, onValueChange = { new ->
                            pagesViewModel.setOption2(currentPage, new)
                        })
                        TextField(value = questions[currentPage].optionC, onValueChange = { new ->
                            pagesViewModel.setOption3(currentPage, new)
                        })
                        TextField(value = questions[currentPage].optionD, onValueChange = { new ->
                            pagesViewModel.setOption4(currentPage, new)
                        })
                    }
                }
            }
            Button(
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp
                ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFE5B54),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    pagesViewModel.savePagesForUser(title)
                    navController.navigate("MainScreen"){
                        popUpTo("MainScreen"){
                            inclusive = true
                        }
                    }
                }) {
                Text(text = "Save and continue")
            }
        }
    }
}

@Preview
@Composable
private fun PagesPreview() {
    PagesScreen(navController = rememberNavController(), title = "")
}