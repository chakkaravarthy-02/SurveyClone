package com.example.zohosurvey.screens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var statusText by rememberSaveable {
        mutableStateOf("Active")
    }
    var reopenText by rememberSaveable {
        mutableStateOf("Close")
    }
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(text = "survey title")
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
                        imageVector = Icons.Filled.ArrowBack,
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
                                Text(fontSize = 50.sp, text = "2")
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
                                    Text(fontSize = 12.sp, color = Color.Gray, text = "1")
                                    Spacer(modifier = Modifier.padding(start = 2.dp))
                                    Text(fontSize = 12.sp, color = Color.Gray, text = "Page")
                                }
                                Spacer(modifier = Modifier.padding(start = 12.dp))
                                Row {
                                    Text(fontSize = 12.sp, color = Color.Gray, text = "0")
                                    Spacer(modifier = Modifier.padding(start = 2.dp))
                                    Text(fontSize = 12.sp, color = Color.Gray, text = "Questions")
                                }
                            }
                            Row {
                                Text(fontSize = 12.sp, color = Color.Gray, text = "2")
                                Spacer(modifier = Modifier.padding(start = 2.dp))
                                Text(fontSize = 12.sp, color = Color.Gray, text = "Survey Visits")
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
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
                                text = statusText,
                                color = if (statusText == "Closed") Color.Red else Color(0xFF059B20)
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
                            Text(fontSize = 12.sp, text = "Oct 14, 2024")
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(fontSize = 12.sp, text = "Latest response on", color = Color.Gray)
                            Text(fontSize = 12.sp, text = "Oct 22, 2024")
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(fontSize = 12.sp, text = "Last modified on", color = Color.Gray)
                            Text(fontSize = 12.sp, text = "Oct 23, 2024")
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
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "go to survey"
                                )
                            }
                        }
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 10.dp)
                                .clickable {
                                    if (reopenText == "Reopen Survey") {
                                        reopenText = "Close"
                                        statusText = "Active"
                                    } else {
                                        showDialog = true
                                    }
                                },
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = if (reopenText == "Close") Color.Red else Color(0xFF059B20),
                            text = reopenText
                        )

                        if (showDialog) {
                            DialogBoxForClose(onDismiss = {
                                showDialog = false
                            }, onConfirm = {
                                reopenText = "Reopen Survey"
                                statusText = "Closed"
                                showDialog = false
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogBoxForClose(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        titleContentColor = Color.Black,
        textContentColor = Color.Gray,
        containerColor = Color.White,
        tonalElevation = 5.dp,
        title = {
            Text(text = "Zoho Survey")
        },
        text = {
            Text(text = "Are you sure you want to close the survey")
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
    DetailScreen(navController = rememberNavController())
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun Dialog() {
    DialogBoxForClose(onDismiss = {}) {

    }
}