package com.example.zohosurvey.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun AboutScreen(navController: NavHostController) {
    val pagerState = rememberPagerState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        verticalArrangement = Arrangement.Center){
        HorizontalPager(
            count = 4,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) { page ->
            when(page){
                0 -> PageContent(R.drawable.search_show_image, "Manage your surveys anywhere at any time.")
                1 -> PageContent(R.drawable.send_show_image, "Send out your surveys at a moment's notice.")
                2 -> PageContent(R.drawable.analyze_show_image, "Analyze the responses real-time.")
                3 -> PageContent(R.drawable.custom_show_image, "Create custom reports on the go.")
            }
        }
        HorizontalPagerIndicator(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            pageCount = 4,
            activeColor = Color(0xFFFE5B54),
            inactiveColor = Color.LightGray
        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)){
            Button(colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFFFE5B54)
            ), modifier = Modifier.weight(1f), onClick = {
                navController.navigate("LoginScreen")
            }) {
                Text(text = "Sign-in")
            }
            Button(colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color(0xFFFE5B54)
            ),modifier = Modifier.weight(1f),onClick = {
                navController.navigate("SignUpScreen")
            }) {
                Text(text = "Sign-up")
            }
        }
    }
}

@Composable
fun PageContent(image: Int, text: String) {
    Column (verticalArrangement = Arrangement.Center, modifier = Modifier.padding(horizontal = 16.dp)){
        Image(modifier = Modifier.size(400.dp), painter = painterResource(id = image), contentDescription = "image")
        Spacer(modifier = Modifier.padding(24.dp))
        Text(textAlign = TextAlign.Center, fontSize = 15.sp, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally).width(240.dp), text = text)
    }
}

@Preview
@Composable
private fun AboutPreview() {
    AboutScreen(navController = rememberNavController())
}
