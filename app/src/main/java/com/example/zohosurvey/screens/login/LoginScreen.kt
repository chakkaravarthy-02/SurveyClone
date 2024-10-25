package com.example.zohosurvey.screens.login

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.example.zohosurvey.viewmodelfactorys.LoginFactory
import com.example.zohosurvey.viewmodels.LoginViewModel
import com.example.zohosurvey.viewmodels.MainViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginFactory(context.applicationContext as Application, context)
    )
    var emailText by rememberSaveable {
        mutableStateOf("")
    }

    var passwordText by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f) // 30% of the screen height
                .background(Color(0xFFFE5B54))
        )

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
        ) {
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, RoundedCornerShape(8.dp))
                    .verticalScroll(rememberScrollState()),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_zoho),
                        contentDescription = "logo"
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(style = MaterialTheme.typography.h5, text = "Sign in")
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(text = "to access Zoho Survey")
                    Spacer(modifier = Modifier.padding(16.dp))
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFFE5B54),
                            unfocusedIndicatorColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = emailText,
                        onValueChange = { newText -> emailText = newText },
                        placeholder = {
                            Text(
                                text = "Email address or mobile number",
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    TextField(
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFFE5B54),
                            unfocusedIndicatorColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = passwordText,
                        onValueChange = { newText -> passwordText = newText },
                        placeholder = {
                            Text(
                                text = "Password",
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFE5B54)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            if (emailText.isNotEmpty() && passwordText.isNotEmpty()) {
                                loginViewModel.isEmailValid(emailText)
                                loginViewModel.isPasswordValid(passwordText)
                                if (loginViewModel.isValidEmail.value == true) {
                                    if (loginViewModel.isValidPassword.value == true) {
                                        loginViewModel.savePreference(emailText)
                                        navController.navigate("MainScreen"){
                                            popUpTo("AboutScreen"){inclusive = true}
                                        }
                                    } else {
                                        passwordText = ""
                                        Toast.makeText(
                                            context,
                                            "Invalid Password",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    emailText = ""
                                    Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } else {
                                Toast.makeText(context, "Field is required", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    ) {
                        Text(text = "Next")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        style = MaterialTheme.typography.h6,
                        fontSize = 16.sp,
                        text = "Sign in using"
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            modifier = Modifier.border(BorderStroke(1.dp, Color.Black)),
                            onClick = {}) {
                            Image(
                                painter = painterResource(id = R.drawable.google_signin),
                                contentDescription = "google-sign-in"
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        IconButton(
                            modifier = Modifier.border(BorderStroke(1.dp, Color.Black)),
                            onClick = {}) {
                            Image(
                                painter = painterResource(id = R.drawable.apple_signin),
                                contentDescription = "apple-sign-in"
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                        IconButton(
                            modifier = Modifier.border(BorderStroke(1.dp, Color.Black)),
                            onClick = {}) {
                            Image(
                                painter = painterResource(id = R.drawable.facebook_signin),
                                contentDescription = "facebook-sign-in"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(fontSize = 12.sp, text = "Don't have a Zoho account?")
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate("SignUpScreen")
                            },
                            fontSize = 12.sp,
                            color = Color(0xFF1871B8),
                            text = "Sign up now"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    val navController = rememberNavController()
    val mainViewModel = MainViewModel(LocalContext.current).apply {
        isLogin.value = false // Set initial login state for preview
    }

    LoginScreen(navController = navController, mainViewModel = mainViewModel)
}

