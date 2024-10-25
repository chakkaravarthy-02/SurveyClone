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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.zohosurvey.R
import com.example.zohosurvey.screens.HorizontalLine
import com.example.zohosurvey.viewmodelfactorys.SignUpFactory
import com.example.zohosurvey.viewmodels.SignUpViewModel

@Composable
fun SignupScreen(navController: NavHostController,signUpViewModel: SignUpViewModel = viewModel(factory = SignUpFactory(LocalContext.current.applicationContext as Application))) {
    val context = LocalContext.current

    var emailText by rememberSaveable {
        mutableStateOf("")
    }
    var passwordText by rememberSaveable {
        mutableStateOf("")
    }
    var phoneNumberText by rememberSaveable {
        mutableStateOf("")
    }
    var agree by rememberSaveable {
        mutableStateOf(false)
    }
    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.width(100.dp),
                            painter = painterResource(id = R.drawable.logo_zoho),
                            contentDescription = "logo"
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(fontSize = 10.sp,text = "Have a Zoho account?")
                            Text(modifier = Modifier.clickable {
                                navController.navigate("LoginScreen")
                            }, fontSize = 10.sp, color = Color(0xFFFE5B54), text = "SIGNIN")
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    HorizontalLine()
                    Spacer(modifier = Modifier.padding(8.dp))
                    Image(
                        modifier = Modifier.width(150.dp),
                        painter = painterResource(id = R.drawable.survey_logo),
                        contentDescription = "survey logo"
                    )
                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        text = "Create your free account now"
                    )

                    Spacer(modifier = Modifier.padding(12.dp))
                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFFE5B54),
                            unfocusedIndicatorColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = Color.Black,
                            unfocusedLabelColor = Color.Gray,
                            focusedLabelColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = emailText,
                        onValueChange = { new -> emailText = new },
                        label = {
                            Text(text = "Email*")
                        }
                    )

                    Spacer(modifier = Modifier.padding(2.dp))
                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFFE5B54),
                            unfocusedIndicatorColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = Color.Black,
                            unfocusedLabelColor = Color.Gray,
                            focusedLabelColor = Color.Black,
                            unfocusedTrailingIconColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = passwordText,
                        onValueChange = { new -> passwordText = new },
                        label = {
                            Text(text = "Password*")
                        },
                        trailingIcon = {
                            IconButton(onClick = {passwordVisible = !passwordVisible}) {
                                Icon(painter = painterResource(id = if(passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24), contentDescription = "Password visibility")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.padding(2.dp))
                    TextField(
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xFFFE5B54),
                            unfocusedIndicatorColor = Color.Gray,
                            unfocusedPlaceholderColor = Color.LightGray,
                            cursorColor = Color.Black,
                            unfocusedLabelColor = Color.Gray,
                            focusedLabelColor = Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = phoneNumberText,
                        onValueChange = { new -> phoneNumberText = new },
                        label = {
                            Text(text = "Phone Number*")
                        }
                    )

                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            modifier = Modifier
                                .clickable { agree = !agree },
                            checked = agree,
                            onCheckedChange = {new -> agree = new },
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(fontSize = 10.sp, text = " I agree to the ")
                            Text(fontSize = 10.sp,color = Color(0xFF1871B8), text = "Terms of service")
                            Text(fontSize = 10.sp,text = " and ")
                            Text(fontSize = 10.sp,color = Color(0xFF1871B8), text = "Privacy Policy")
                        }
                    }

                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFE5B54)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        onClick = {
                            if(agree){
                                if(emailText.isNotEmpty() && passwordText.isNotEmpty() && phoneNumberText.isNotEmpty()){
                                    val inserted = signUpViewModel.insertUserDetails(emailText,passwordText,phoneNumberText)
                                    if(inserted) {
                                        navController.navigate("AboutScreen") {
                                            popUpTo("AboutScreen") {
                                                inclusive = true
                                            }
                                        }
                                        Toast.makeText(context,"Data saved",Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Error in inserting... Try again", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }else{
                                    Toast.makeText(context,"All fields are required",Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                Toast.makeText(context,"Please read terms and policy and check that",Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(text = "SIGN UP")
                    }

                    Spacer(modifier = Modifier.padding(12.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "or sign in using"
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            modifier = Modifier.border(BorderStroke(1.dp, Color.Black)),
                            onClick = {

                            },
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.google_signin),
                                contentDescription = "google-sign-in"
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))

                        Image(
                            modifier = Modifier
                                .size(49.dp)
                                .clickable {

                                },
                            painter = painterResource(id = R.drawable.linkedin_signup),
                            contentDescription = "linkedin-sign-in"
                        )

                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignupPreview() {
    SignupScreen(navController = rememberNavController())
}

