package com.example.zohosurvey.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.example.zohosurvey.viewmodelfactorys.LinkFactory
import com.example.zohosurvey.viewmodels.LinkViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatedLinkScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    linkViewModel: LinkViewModel = viewModel(factory = LinkFactory(LocalContext.current)),
    title: String?
) {

    LaunchedEffect(Unit) {
        linkViewModel.getLink(title)  // Call the function once
    }
    val context = LocalContext.current

    val link = linkViewModel.link.collectAsState()

    Surface {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TopAppBar(
                title = {
                    Image(
                        modifier = Modifier
                            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                            .size(120.dp),
                        painter = painterResource(id = R.drawable.survey_logo),
                        contentDescription = "logo"
                    )
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
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
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(16.dp),
                ) {
                    Text(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        text = "App Link",
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Text(color = Color.Black,text = "Use this link for your survey")
                    Spacer(modifier = Modifier.padding(top = 24.dp))
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RectangleShape)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            color = Color(0xFF3296C4),
                            modifier = Modifier.padding(12.dp),
                            maxLines = 1,
                            text = link.value,
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Button(elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp
                    ), colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFE5B54),
                        contentColor = Color.White
                    ), onClick = {}, modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Access Survey")
                    }
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Button(
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 10.dp
                        ),
                        border = ButtonDefaults.outlinedButtonBorder,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            copy(link.value, context)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Row {
                            Image(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(id = R.drawable.copy),
                                contentDescription = "copy"
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(text = "Copy Link")
                        }
                    }
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    Button(elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp
                    ),
                        border = ButtonDefaults.outlinedButtonBorder,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        onClick = {
                            share(link.value, context)
                        },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Row {
                            Image(
                                modifier = Modifier.size(22.dp),
                                painter = painterResource(id = R.drawable.share),
                                contentDescription = "copy"
                            )
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(text = "Share Link")
                        }
                    }
                }
            }
        }
    }
}

fun copy(link: String, context: Context){
    val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("Survey Link", link)
    clipboard.setPrimaryClip(clipData)
}

fun share(link: String, context: Context) {
    val shareIntent = Intent().apply{
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, link)
        type = "text/plain"
    }
    context.startActivity(Intent.createChooser(shareIntent,"Share link via"))
}

@Preview
@Composable
private fun CreatedLinkPreview() {
    CreatedLinkScreen(navController = rememberNavController(), title = "")
}