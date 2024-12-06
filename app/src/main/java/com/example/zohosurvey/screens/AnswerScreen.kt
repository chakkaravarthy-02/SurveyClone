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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zohosurvey.viewmodelfactorys.AnswerFactory
import com.example.zohosurvey.viewmodels.AnswerViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    link: String?,
    answerViewModel: AnswerViewModel = viewModel(
        factory = AnswerFactory(LocalContext.current)
    )
) {

    val pagerState = rememberPagerState()
    val context = LocalContext.current

    val list by answerViewModel.questions.collectAsState()

    val selectedOptions = answerViewModel.options.collectAsState()

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
                title = { Text(fontWeight = FontWeight.SemiBold, text = "Survey") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("AddingScreen")
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                    }
                }
            )
            Spacer(modifier = Modifier.padding(2.dp))
            if(list.isNotEmpty()){
                val survey = answerViewModel.filterList(list, link)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(color = Color.Black, text = "Page ")
                    Text(color = Color.Black, text = (pagerState.currentPage + 1).toString() + "/")
                    Text(color = Color.Black, text = survey?.pages.toString())
                }
                Spacer(modifier = Modifier.padding(2.dp))
                survey?.pages?.let {
                    LaunchedEffect(survey.pages) {
                        if (selectedOptions.value.isEmpty() && survey.pages != null) {
                            answerViewModel.setListToNull(survey.pages!!)
                        }
                    }
                    HorizontalPager(
                        count = it.toInt(),
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
                                Text(text = "Question")
                                survey.questionData?.get(currentPage)?.get("questionText")
                                    ?.let { it1 -> Text(text = it1) }
                                Text(text = "Choose your option")
                                val options = listOf(
                                    "optionA" to survey.questionData?.get(currentPage)?.get("optionA"),
                                    "optionB" to survey.questionData?.get(currentPage)?.get("optionB"),
                                    "optionC" to survey.questionData?.get(currentPage)?.get("optionC"),
                                    "optionD" to survey.questionData?.get(currentPage)?.get("optionD")
                                )

                                if(selectedOptions.value.isNotEmpty()){
                                    options.forEach { (key, optionText) ->
                                        if (optionText != null) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                RadioButton(
                                                    selected = selectedOptions.value[currentPage] == key,
                                                    onClick = {
                                                        answerViewModel.setOption(currentPage, key)
                                                    }
                                                )
                                                Text(optionText)
                                            }
                                        }
                                    }
                                }
                            }
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
                        println(selectedOptions.value)
                        answerViewModel.updateAnswers(survey,selectedOptions.value)
                        navController.navigate("LinkScreen") {
                            popUpTo("LinkScreen") {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text(text = "Submit")
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}