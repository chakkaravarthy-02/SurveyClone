package com.example.zohosurvey.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.example.zohosurvey.util.encode
import com.example.zohosurvey.viewmodelfactorys.DetailFactory
import com.example.zohosurvey.viewmodels.DetailViewModel
import com.example.zohosurvey.viewmodels.MainViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    detailViewModel: DetailViewModel = viewModel(
        factory = DetailFactory(LocalContext.current)
    ),
    id: Int
) {

    val surveyList by detailViewModel.list.collectAsState()
    println("in detail screen")

    var showCloseDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showDeleteDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(text = if (surveyList.isNotEmpty()) surveyList[id].title.toString() else "Loading...")
            },
            actions = {
                IconButton(onClick = {
                    showDeleteDialog = true
                }) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "delete"
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("MainScreen") {
                        popUpTo("MainScreen") {
                            inclusive = true
                        }
                    }
                }) {
                    Icon(
                        tint = Color.White,
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFE5B54),
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
        if (showDeleteDialog) {
            DialogBox(
                text = "Are you sure you want to delete the survey",
                onDismiss = {
                    showDeleteDialog = false
                }, onConfirm = {
                    detailViewModel.deleteSurvey(title = surveyList[id].title.toString())
                    showDeleteDialog = false
                    navController.navigate("MainScreen") {
                        popUpTo("MainScreen") {
                            inclusive = true
                        }
                    }
                })
        }
        Box(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .background(Color.LightGray)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    if (surveyList.isNotEmpty()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                modifier = Modifier.align(Alignment.Start),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                text = "Summary"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        fontSize = 50.sp,
                                        text = (surveyList[id].response ?: 0).toString()
                                    )
                                    Text(fontSize = 15.sp, text = "Total Responses")
                                }
                                VerticalLine(value = 110)
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(fontSize = 20.sp, text = "2")
                                        Spacer(modifier = Modifier.padding(6.dp))
                                        Text(fontSize = 15.sp, text = "Completed")
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(fontSize = 20.sp, text = "0")
                                        Spacer(modifier = Modifier.padding(6.dp))
                                        Text(fontSize = 15.sp, text = "Partial")
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(8.dp))
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    containerColor = Color.Black
                                ),
                                modifier = Modifier.size(width = 140.dp, height = 60.dp),
                                shape = RectangleShape,
                                onClick = {}
                            ) {
                                Text(fontSize = 15.sp, text = "View Report")
                            }
                            Spacer(modifier = Modifier.padding(16.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row {
                                    Row {
                                        Text(
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            text = (surveyList[id].pages).toString()
                                        )
                                        Spacer(modifier = Modifier.padding(start = 2.dp))
                                        Text(fontSize = 12.sp, color = Color.Gray, text = "Page")
                                    }
                                    Spacer(modifier = Modifier.padding(start = 12.dp))
                                    Row {
                                        Text(
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            text = surveyList[id].questionData?.size.toString()
                                        )
                                        Spacer(modifier = Modifier.padding(start = 2.dp))
                                        Text(
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            text = "Questions"
                                        )
                                    }
                                }
                                Row {
                                    Text(
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        text = (surveyList[id].visits ?: 0).toString()
                                    )
                                    Spacer(modifier = Modifier.padding(start = 2.dp))
                                    Text(
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        text = "Survey Visits"
                                    )
                                }
                            }
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    if (surveyList.isNotEmpty()) {
                        if (surveyList[id].isPublished == true) {
                            Column(
                                modifier = Modifier
                                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.Start),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    text = "Details"
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(fontSize = 12.sp, text = "Status", color = Color.Gray)
                                    Text(
                                        fontSize = 12.sp,
                                        text = surveyList[id].status.toString(),
                                        color = if (surveyList[id].status.toString() == "Closed") Color.Red else Color(
                                            0xFF059B20
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.padding(4.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(fontSize = 12.sp, text = "Created on", color = Color.Gray)
                                    Text(
                                        fontSize = 12.sp,
                                        text = surveyList[id].createdTime.toString()
                                    )
                                }
                                Spacer(modifier = Modifier.padding(4.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        fontSize = 12.sp,
                                        text = "Latest response on",
                                        color = Color.Gray
                                    )
                                    Text(
                                        fontSize = 12.sp,
                                        text = surveyList[id].responseTime.toString()
                                    )
                                }
                                Spacer(modifier = Modifier.padding(4.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        fontSize = 12.sp,
                                        text = "Last modified on",
                                        color = Color.Gray
                                    )
                                    Text(
                                        fontSize = 12.sp,
                                        text = surveyList[id].modifiedTime.toString()
                                    )
                                }
                                Spacer(modifier = Modifier.padding(8.dp))
                                Text(
                                    modifier = Modifier.align(Alignment.Start),
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    text = "Actions"
                                )
                                Spacer(modifier = Modifier.padding(2.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(fontSize = 12.sp, text = "View Survey", color = Color.Gray)
                                    IconButton(onClick = {
                                        val encodedLink = URLEncoder.encode(surveyList[id].link, StandardCharsets.UTF_8.toString())
                                        navController.navigate("AnswerScreen/${encodedLink}")
                                    }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                            contentDescription = "go to survey"
                                        )
                                    }
                                }
                                Text(
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(bottom = 10.dp)
                                        .clickable {
                                            if (surveyList[id].status.toString() == "Closed") {
                                                detailViewModel.changeStatus(
                                                    surveyList[id].title.toString(),
                                                    "Active"
                                                )
                                            } else {
                                                showCloseDialog = true
                                            }
                                        },
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    color = if (surveyList[id].status.toString() == "Closed") Color(
                                        0xFF059B20
                                    ) else Color.Red,
                                    text = if (surveyList[id].status.toString() == "Closed") "Reopen Survey" else "Close"
                                )

                                if (showCloseDialog) {
                                    DialogBox(
                                        text = "Are you sure you want to close the survey",
                                        onDismiss = {
                                            showCloseDialog = false
                                        }, onConfirm = {
                                            detailViewModel.changeStatus(
                                                surveyList[id].title.toString(),
                                                "Closed"
                                            )
                                            showCloseDialog = false
                                        })
                                }
                            }
                        } else {
                            Column(
                                modifier = modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.lanch),
                                    contentDescription = "publish image"
                                )
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Color.White,
                                        containerColor = Color(0xFFFE5B54)
                                    ), onClick = {
                                        detailViewModel.publishThisSurvey(title = surveyList[id].title.toString())
                                        navController.navigate("CreatedLinkScreen&title=${surveyList[id].title}")
                                    }) {
                                    Text(text = "Publish")
                                }
                            }
                        }
                    } else {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogBox(text: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        titleContentColor = Color.Black,
        textContentColor = Color.Gray,
        containerColor = Color.White,
        tonalElevation = 5.dp,
        title = {
            Text(text = "Zoho Survey")
        },
        text = {
            Text(text = text)
        },
        shape = RoundedCornerShape(5.dp),
        onDismissRequest = {
            onDismiss()
        }, confirmButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFFFE5B54)
            ), onClick = { onConfirm() }) {
                Text(text = "Yes")
            }
        }, dismissButton = {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black
            ), onClick = { onDismiss() }) {
                Text(text = "No")
            }
        })
}

@Preview
@Composable
private fun DetailPreview() {
    DetailScreen(navController = rememberNavController(), id = 0)
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Dialog() {
    DialogBox("", onDismiss = {}) {

    }
}