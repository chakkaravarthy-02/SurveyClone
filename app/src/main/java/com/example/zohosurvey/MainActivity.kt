package com.example.zohosurvey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") { EntireMainScreen(navController = navController) }
        composable("AddingScreen") { AddingScreen(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithMenuDrawer() {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    TopAppBar(colors = TopAppBarColors(
        containerColor = Color(0xFFFE5B54),
        navigationIconContentColor = Color.White,
        titleContentColor = Color.White,
        actionIconContentColor = Color.White,
        scrolledContainerColor = Color(0xFFFE5B54)
    ), title = { Text(text = "Title") }, navigationIcon = {
        IconButton(onClick = {

        }) {
            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu Drawer")
        }
    }, actions = {
        IconButton(onClick = {
            showDialog = true
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_filter_alt_24),
                contentDescription = "Filter"
            )
        }
        IconButton(onClick = { /* Handle search action */ }) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }
    })

    if (showDialog) {
        FilterDialog(onClick = {
            showDialog = false
        })
    }
}

@Composable
fun FilterDialog(onClick: () -> Unit) {
    var selectedOption by rememberSaveable { mutableStateOf("All") } // Initially selected option
    val options = listOf("All", "Published", "Draft", "Closed")

    Dialog(
        onDismissRequest = { onClick() }
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 12.dp), color = Color.Black, text = option
                    )
                    RadioButton(
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color(0xFFFE5B54)
                        ),
                        selected = selectedOption == option,
                        onClick = {
                            selectedOption = option
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun EntireMainScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarWithMenuDrawer()
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(R.drawable.empty),
                    contentDescription = "Nothing to show"
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(color = Color.Gray, text = "You don't have any surveys")
            }
            FloatingActionButton(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp),
                containerColor = Color(0xFFFE5B54),
                contentColor = Color.White,
                onClick = {
                    navController.navigate("AddingScreen")
                }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
            }
        }
    }
}

@Preview
@Composable
private fun EntirePreview() {
    EntireMainScreen(navController = rememberNavController())
}

@Preview
@Composable
private fun AddingPreview() {
    AddingScreen(navController = rememberNavController())
}

@Preview
@Composable
private fun DialogPreview() {
    FilterDialog(onClick = {})
}
