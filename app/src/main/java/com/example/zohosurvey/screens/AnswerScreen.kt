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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnswerScreen(modifier: Modifier = Modifier, navController: NavController) {

    val pagerState = rememberPagerState()

    var isFirstOption by rememberSaveable {
        mutableStateOf(false)
    }
    var isSecondOption by rememberSaveable {
        mutableStateOf(false)
    }
    var isThirdOption by rememberSaveable {
        mutableStateOf(false)
    }
    var isFourthOption by rememberSaveable {
        mutableStateOf(false)
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
                title = { Text(fontWeight = FontWeight.SemiBold, text = "Survey") },
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
                Text(color = Color.Black, text = "/2")
            }
            Spacer(modifier = Modifier.padding(2.dp))
            HorizontalPager(
                count = 2,
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
                        Text(text = "Choose your option")
                        RadioButton(selected = isFirstOption, onClick = {
                            isFirstOption = !isFirstOption
                            isSecondOption = false
                            isThirdOption = false
                            isFourthOption = false
                        })
                        RadioButton(selected = isSecondOption, onClick = {
                            isSecondOption = !isSecondOption
                            isFirstOption = false
                            isThirdOption = false
                            isFourthOption = false
                        })
                        RadioButton(selected = isThirdOption, onClick = {
                            isFirstOption = false
                            isSecondOption = false
                            isFourthOption = false
                            isThirdOption = !isThirdOption
                        })
                        RadioButton(selected = isFourthOption, onClick = {
                            isFourthOption = !isFourthOption
                            isFirstOption = false
                            isSecondOption = false
                            isThirdOption = false
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
                    navController.navigate("LinkScreen") {
                        popUpTo("LinkScreen") {
                            inclusive = true
                        }
                    }
                }) {
                Text(text = "Submit")
            }
        }
    }
}