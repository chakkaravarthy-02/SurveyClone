package com.example.zohosurvey

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.screens.AddingScreen
import com.example.zohosurvey.screens.EntireMainScreen
import com.example.zohosurvey.screens.SearchScreen
import com.example.zohosurvey.screens.login.AboutScreen
import com.example.zohosurvey.screens.login.LoginScreen
import com.example.zohosurvey.screens.login.SignupScreen
import com.example.zohosurvey.ui.theme.ZohoSurveyTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZohoSurveyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    val isLogin = false
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color(0xFFFE5B54)
        )
        systemUiController.setNavigationBarColor(
            color = Color.Black
        )
    }
    val navController = rememberNavController()

    if(isLogin) {
        NavHost(navController = navController, startDestination = "MainScreen") {
            composable("MainScreen") { EntireMainScreen(navController = navController) }
            composable("AddingScreen") { AddingScreen(navController) }
            composable("SearchScreen") { SearchScreen(navController) }
        }
    }else{
        NavHost(navController = navController, startDestination = "AboutScreen") {
            composable("AboutScreen") { AboutScreen(navController) }
            composable("LoginScreen") { LoginScreen(navController) }
            composable("SignUpScreen") { SignupScreen(navController) }
        }
    }
}

fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}