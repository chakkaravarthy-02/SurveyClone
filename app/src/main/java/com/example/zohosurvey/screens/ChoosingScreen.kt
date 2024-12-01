package com.example.zohosurvey.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoosingScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    Column {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = Color(0xFFFE5B54),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
                scrolledContainerColor = Color(0xFFFE5B54)
            ),
            title = {},
            navigationIcon = {
                Image(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                        .size(120.dp),
                    painter = painterResource(id = R.drawable.survey_logo),
                    contentDescription = "logo"
                )
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Card(
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(0xFFFE5B54),
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 24.dp)
                        .border(width = 1.dp, color = Color(0xFFFE5B54),
                            RoundedCornerShape(10.dp)
                        ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        navController.navigate("MainScreen")
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier.align(Alignment.Center),
                            text = "Admin"
                        )
                    }
                }
                Card(
                    colors = CardDefaults.elevatedCardColors(
                        contentColor = Color(0xFFFE5B54),
                        containerColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 24.dp)
                        .border(width = 1.dp, color = Color(0xFFFE5B54),
                            RoundedCornerShape(10.dp)),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    ),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        navController.navigate("LinkScreen")
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            modifier = Modifier.align(Alignment.Center),
                            text = "User"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ChoosingPreview() {
    ChoosingScreen(navController = rememberNavController())
}