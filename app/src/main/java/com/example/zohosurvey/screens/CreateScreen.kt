package com.example.zohosurvey.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddingScreen(navController: NavHostController) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    var category by rememberSaveable {
        mutableStateOf("Select a category")
    }
    var surveyName by rememberSaveable {
        mutableStateOf("")
    }
    Column {
        TopAppBar(colors = TopAppBarColors(
            containerColor = Color(0xFFFE5B54),
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            scrolledContainerColor = Color(0xFFFE5B54)
        ),
            title = { Text(fontWeight = FontWeight.SemiBold, text = "Create survey") },
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
            })
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFFF8F6F6)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 14.dp)
            ) {
                //name text
                Column {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp,
                        text = "Survey Name"
                    )
                    //name textField
                    TextField(colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFFE5B54),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTrailingIconColor = Color(0xFFFE5B54),
                        cursorColor = Color.Black
                    ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = surveyName,
                        onValueChange = { newText -> surveyName = newText },
                        trailingIcon = {
                            IconButton(onClick = {
                                surveyName = ""
                            }) {
                                Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear")
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    //category
                    Button(border = BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = {
                            expanded = true
                        }) {
                        Text(text = category)
                    }
                    DropdownMenu(modifier = Modifier.background(Color(0xFFFE5B54)),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")

                        // Create a menu item for each option
                        options.forEach { option ->
                            DropdownMenuItem(text = {
                                Text(
                                    text = option,
                                    color = Color.White // Set text color for menu items
                                )
                            }, onClick = {
                                category = option // Update button text
                                expanded = false // Close the dropdown menu
                            })
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFE5B54), contentColor = Color.White
                        )
                    ) {
                        Text(text = "Create", fontSize = 15.sp)
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun AddingPreview() {
    AddingScreen(navController = rememberNavController())
}