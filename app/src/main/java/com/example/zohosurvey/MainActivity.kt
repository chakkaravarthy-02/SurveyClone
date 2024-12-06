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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zohosurvey.screens.AddingScreen
import com.example.zohosurvey.screens.AnswerScreen
import com.example.zohosurvey.screens.ChoosingScreen
import com.example.zohosurvey.screens.CreatedLinkScreen
import com.example.zohosurvey.screens.DetailScreen
import com.example.zohosurvey.screens.EntireMainScreen
import com.example.zohosurvey.screens.LinkScreen
import com.example.zohosurvey.screens.PagesScreen
import com.example.zohosurvey.screens.SearchScreen
import com.example.zohosurvey.screens.login.AboutScreen
import com.example.zohosurvey.screens.login.LoginScreen
import com.example.zohosurvey.screens.login.SignupScreen
import com.example.zohosurvey.ui.theme.ZohoSurveyTheme
import com.example.zohosurvey.viewmodelfactorys.MainFactory
import com.example.zohosurvey.viewmodels.MainViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import android.content.Intent
import android.net.Uri
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ATTENTION: This was auto-generated to handle app links.
        val appLinkIntent: Intent = intent
        val appLinkAction: String? = appLinkIntent.action
        val appLinkData: Uri? = appLinkIntent.data

        if (appLinkData != null) {
            println("*^@${appLinkData.lastPathSegment}")
        }
        setContent {
            ZohoSurveyTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp(
    modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel(
        factory = MainFactory(
            LocalContext.current
        )
    )
) {

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

    mainViewModel.checkUserLoggedIn()

    NavHost(
        navController = navController,
        startDestination = if (mainViewModel.isLogin.value) "ChoosingScreen" else "AboutScreen"
    ) {
        composable("MainScreen") { EntireMainScreen(navController = navController) }
        composable("LinkScreen") { LinkScreen(navController = navController) }
        composable(
            "AnswerScreen/{link}",
            arguments = listOf(navArgument("link"){type = NavType.StringType})
        ) {
            val encodedLink = it.arguments?.getString("link")
            val link = URLDecoder.decode(encodedLink, StandardCharsets.UTF_8.toString())
            AnswerScreen(navController = navController, link = link)
        }
        composable("AddingScreen") { AddingScreen(navController) }
        composable("ChoosingScreen") { ChoosingScreen(navController = navController) }
        composable("SearchScreen") { SearchScreen(navController) }
        composable("AboutScreen") { AboutScreen(navController) }
        composable("LoginScreen") { LoginScreen(navController, mainViewModel = mainViewModel) }
        composable("SignUpScreen") { SignupScreen(navController) }
        composable(
            "CreatedLinkScreen&title={title}",
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) {
            val title = it.arguments?.getString("title")
            CreatedLinkScreen(navController = navController, title = title)
        }
        composable(
            "DetailScreen/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt("id") ?: 0
            DetailScreen(navController = navController, id = id)
        }
        composable(
            "CreatePagesScreen/{Survey Title}",
            arguments = listOf(navArgument("Survey Title") { type = NavType.StringType })
        ) {
            val title = it.arguments?.getString("Survey Title") ?: "None"
            PagesScreen(navController = navController, title = title)
        }
    }
}

fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
        NetworkCapabilities.TRANSPORT_WIFI
    )
}