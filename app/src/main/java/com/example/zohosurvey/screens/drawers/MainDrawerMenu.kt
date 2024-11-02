package com.example.zohosurvey.screens.drawers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material.icons.sharp.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.example.zohosurvey.screens.HorizontalLine
import com.example.zohosurvey.screens.VerticalLine
import com.example.zohosurvey.viewmodelfactorys.MainDrawerFactory
import com.example.zohosurvey.viewmodels.MainDrawerViewModel

@Composable
fun DrawerContent(navController: NavHostController,onItemClicked: () -> Unit, onDepartmentClicked: () -> Unit,mainDrawerViewModel: MainDrawerViewModel = viewModel(factory = MainDrawerFactory(
    LocalContext.current))) {

    val widthDp = 400.dp
    val emailState by mainDrawerViewModel.email.observeAsState()

    Column(modifier = Modifier
        .width(widthDp)
        .fillMaxHeight()
        .padding(end = 60.dp)
        .verticalScroll(rememberScrollState())
        .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(180.dp)
                .background(Color.Black)
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            Column(modifier = Modifier.padding(start = 8.dp)) {
                Image(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    painter = painterResource(id = R.drawable.emptyprofile),
                    contentDescription = "empty_Image",
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    color = Color.White,
                    text = "Saran Sarath",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    color = Color.White,
                    text = emailState.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween){
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                    Row{
                        Image(
                            modifier = Modifier
                                .size(width = 40.dp, height = 40.dp)
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.baseline_language_24),
                            contentDescription = "globe"
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Column(modifier = Modifier.padding(end = 2.dp)) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                text = "Saran chakkaravarthy",
                                fontSize = 13.sp
                            )
                            Text(
                                color = Color(0xFF272626),
                                text = "My Department",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 15.sp
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically){
                        IconButton(onClick = {
                            onDepartmentClicked()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "keyboard right arrow"
                            )
                        }
                        VerticalLine(70)
                        IconButton(modifier = Modifier
                            .padding(start = 12.dp)
                            .align(Alignment.CenterVertically),
                            onClick = {},
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_settings_24),
                                contentDescription = "Settings"
                            )
                        }
                    }
                }
                HorizontalLine()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            text = "3",
                            fontSize = 53.sp
                        )
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onItemClicked()
                                }
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 8.dp), text = "Total Surveys"
                        )
                    }
                    VerticalLine(value = 100)
                    Column(
                        modifier = Modifier.padding(vertical = 10.dp),
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Row {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                text = "0",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onItemClicked()
                                    }
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp), text = "Published"
                            )
                        }
                        Row {
                            Text(
                                modifier = Modifier,
                                text = "0",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        onItemClicked()
                                    }
                                    .align(Alignment.CenterVertically)
                                    .padding(start = 8.dp), text = "Drafts"
                            )
                        }
                    }
                }
                HorizontalLine()
                DrawerBalanceContent(Icons.Sharp.Share, "Shared with me", onItemClicked)
                DrawerBalanceIntContent(navController,R.drawable.verified_user_24px, "Data Permission",onItemClicked)
                DrawerBalanceIntContent(navController,R.drawable.help_24px, "Help", onItemClicked)
                DrawerBalanceContent(Icons.Filled.Lock, "Privacy Policy", onItemClicked)
                DrawerBalanceContent(Icons.Sharp.Settings, "Settings", onItemClicked)
                DrawerBalanceIntContent(navController,R.drawable.logout_24px, "Log Out", onItemClicked)
            }
        }
    }
}

@Composable
fun DrawerBalanceIntContent(navController: NavHostController,vectorInt: Int, content: String, onItemClicked: () -> Unit,mainDrawerViewModel: MainDrawerViewModel = viewModel( factory = MainDrawerFactory(
    LocalContext.current)
)) {
    Row(
        modifier = Modifier
            .clickable {
                onItemClicked()
                when (content) {
                    "Log Out" -> {
                        navController.navigate("AboutScreen")
                        mainDrawerViewModel.logout()
                    }

                    "Help" -> {}
                    "Data Permission" -> {}
                }
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.Top), onClick = {}) {
            Icon(painter = painterResource(id = vectorInt), contentDescription = "Share")
        }
        Text(
            color = Color(0xFF272626),
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            text = content,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun DrawerBalanceContent(iconVector: ImageVector, content: String, onItemClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onItemClicked()
                when (content) {
                    "Shared with me" -> {}
                    "Privacy Policy" -> {}
                    "Settings" -> {}
                }
            }
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {
        IconButton(
            modifier = Modifier
                .align(Alignment.Top), onClick = {}) {
            Icon(imageVector = iconVector, contentDescription = "Share")
        }
        Text(
            color = Color(0xFF272626),
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            text = content,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun DrawerPreview() {
    DrawerContent(
        rememberNavController(),
        onItemClicked = {},
        onDepartmentClicked = {
            var departmentMenu = true
        }
    )
}

@Preview
@Composable
private fun BalanceContentPreview() {
    DrawerBalanceContent(Icons.Sharp.Share, "Shared with me", onItemClicked={})
}