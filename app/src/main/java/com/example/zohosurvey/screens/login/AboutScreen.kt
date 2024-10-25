package com.example.zohosurvey.screens.login

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.example.zohosurvey.checkInternetConnection
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@Composable
fun AboutScreen(navController: NavHostController) {
    val context = LocalContext.current
    val isConnected = rememberSaveable {
        mutableStateOf(checkInternetConnection(context))
    }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                isConnected.value = checkInternetConnection(p0!!)
            }
        }

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
    val pagerState = rememberPagerState()
    Box(modifier = Modifier.fillMaxSize()){
        if (isConnected.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center
            ) {
                HorizontalPager(
                    count = 4,
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> PageContent(
                            R.drawable.search_show_image,
                            "Manage your surveys anywhere at any time."
                        )

                        1 -> PageContent(
                            R.drawable.send_show_image,
                            "Send out your surveys at a moment's notice."
                        )

                        2 -> PageContent(
                            R.drawable.analyze_show_image,
                            "Analyze the responses real-time."
                        )

                        3 -> PageContent(
                            R.drawable.custom_show_image,
                            "Create custom reports on the go."
                        )
                    }
                }
                HorizontalPagerIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    pagerState = pagerState,
                    pageCount = 4,
                    activeColor = Color(0xFFFE5B54),
                    inactiveColor = Color.LightGray
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
                    ), modifier = Modifier.weight(1f), onClick = {
                        navController.navigate("SignUpScreen")
                    }) {
                        Text(text = "Sign-up")
                    }
                }
            }
        } else {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(80.dp),
                    painter = painterResource(id = R.drawable.baseline_signal_wifi_connected_no_internet_4_24),
                    contentDescription = "no internet"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(color = Color.Gray, text = "No internet")
            }
            Toast.makeText(context, "Check your internet connectivity", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun PageContent(image: Int, text: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Image(
            modifier = Modifier.size(400.dp),
            painter = painterResource(id = image),
            contentDescription = "image"
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Text(
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(240.dp),
            text = text
        )
    }
}

@Preview
@Composable
private fun AboutPreview() {
    AboutScreen(navController = rememberNavController())
}
