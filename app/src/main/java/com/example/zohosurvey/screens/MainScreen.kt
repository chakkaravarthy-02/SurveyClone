package com.example.zohosurvey.screens

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.example.zohosurvey.checkInternetConnection
import com.example.zohosurvey.screens.drawers.DepartmentDrawerContent
import com.example.zohosurvey.screens.drawers.DrawerContent
import com.example.zohosurvey.viewmodelfactorys.MainFactory
import com.example.zohosurvey.viewmodels.GetSurvey
import com.example.zohosurvey.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithMenuDrawer(navController: NavHostController, onClick: () -> Unit) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    Box {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = Color(0xFFFE5B54),
                navigationIconContentColor = Color.White,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White,
                scrolledContainerColor = Color(0xFFFE5B54)
            ), title = { Text(text = "Title") },
            navigationIcon = {
                IconButton(onClick = {
                    onClick()
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
                IconButton(onClick = {
                    navController.navigate("SearchScreen")
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                }
            }
        )
        if (showDialog) {
            FilterDialog(onClick = {
                showDialog = false
            })
        }
    }
}

@Composable
fun FilterDialog(onClick: () -> Unit, viewModel: MainViewModel = viewModel()) {
    val selectedOption = viewModel.selectedOption
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
                            viewModel.updateOptionInFilter(option)
                            onClick()
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun EntireMainScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(
        factory = MainFactory(
            LocalContext.current
        )
    )
) {
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
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val openDrawer = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    var departmentMenu by rememberSaveable {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = openDrawer,
        drawerContent = {
            if (departmentMenu) {
                DepartmentDrawerContent(onDepartmentClicked = {
                    departmentMenu = false
                }, onItemClicked = {
                    scope.launch { openDrawer.close() }
                })
            } else {
                DrawerContent(navController,
                    onDepartmentClicked = {
                        departmentMenu = true
                    }, onItemClicked = {
                        scope.launch { openDrawer.close() }
                    })
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        gesturesEnabled = true
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarWithMenuDrawer(onClick = {
                scope.launch { openDrawer.open() }
            }, navController = navController)
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                if (isConnected.value) {
                    if (viewModel.list.isEmpty()) {
                        Column(modifier = Modifier.align(Alignment.Center)) {
                            Image(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                painter = painterResource(R.drawable.empty),
                                contentDescription = "Nothing to show"
                            )
                            Spacer(modifier = Modifier.padding(8.dp))
                            Text(color = Color.Gray, text = "You don't have any surveys")
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            viewModel.list = viewModel.list.sortedBy { it.createdAt }.toMutableList()
                            items(viewModel.list) {
                                ListRow(getSurvey = it,navController = navController)
                            }
                        }
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
                    Toast.makeText(context, "Check your internet connectivity", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}

@Composable
fun LazyListState.isScrollingUp(): State<Boolean> {
    return produceState(initialValue = true) {
        var lastIndex = 0
        var lastScroll = Int.MAX_VALUE
        snapshotFlow {
            firstVisibleItemIndex to firstVisibleItemScrollOffset
        }.collect { (currentIndex, currentScroll) ->
            if (currentIndex != lastIndex || currentScroll != lastScroll) {
                value = currentIndex < lastIndex ||
                        (currentIndex == lastIndex && currentScroll < lastScroll)
                lastIndex = currentIndex
                lastScroll = currentScroll
            }
        }
    }
}

@Composable
fun VerticalLine(value: Int) {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .height(value.dp)
            .width(1.dp)
    )
}

@Composable
fun HorizontalLine() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp
    )
}

@Composable
fun ListRow(getSurvey: GetSurvey, modifier: Modifier = Modifier, navController: NavHostController) {
    var showCount by rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
            .clickable {
                navController.navigate("DetailScreen")
            }
    ) {
        Column {
            Text(text = getSurvey.title.toString())
            Spacer(modifier = Modifier.padding(6.dp))
            Row {
                Text(fontSize = 11.sp, color = Color.LightGray, text = "Last modified on: ")
                Text(fontSize = 11.sp, color = Color.LightGray, text = getSurvey.modified.toString())
                Text(fontSize = 11.sp, text = " 13:37:45", color = Color.LightGray)
            }
            Spacer(modifier = Modifier.padding(2.dp))
            if (getSurvey.isPublished==true) {
                Row {
                    Text(fontSize = 11.sp, color = Color.LightGray, text = "Latest response on: ")
                    Text(fontSize = 11.sp, text = "oct 13,2024", color = Color.LightGray)
                    Text(fontSize = 11.sp, text = " 13:37:45", color = Color.LightGray)
                }
            }
        }
        if (getSurvey.isPublished == true) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    color = Color(0xFFFE5B54),
                    textAlign = TextAlign.Center,
                    fontSize = 40.sp,
                    text = "3"
                )
                Text(text = "Responses")
            }
        }
        if (getSurvey.isPublished == false) {
            Button(colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color(0xFF64B1B0)
            ),
                shape = RectangleShape,
                border = BorderStroke(1.dp, Color(0xFF64B1B0)),
                onClick = {
                    showCount = true
                }) {
                Text(text = "Publish")
            }
        }
    }
}

@Preview
@Composable
private fun ListPreview() {
    ListRow(GetSurvey("", listOf(mapOf("" to "")),"",9,"",false,"","",9,9,"",9,9,9,9,9,9),navController = rememberNavController())
}

@Preview(name = "Preview", showBackground = true)
@Composable
private fun EntirePreview() {
    EntireMainScreen(navController = rememberNavController())
}

@Preview
@Composable
private fun DialogPreview() {
    FilterDialog(onClick = {})
}
