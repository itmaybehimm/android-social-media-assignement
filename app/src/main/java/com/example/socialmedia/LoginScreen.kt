package com.example.socialmedia

import android.util.Log
import android.util.Log.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.data.viewmodel.UserViewModel
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.format.TextStyle

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val currentUser by userViewModel.currentUser.observeAsState()

    LaunchedEffect(Unit) {
        if (currentUser != null) {
            userViewModel.logout {
                d("LoginScreen", "Clearing existing session at LoginScreen launch ${currentUser}")
            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_a),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            backgroundColor = MaterialTheme.colors.surface.copy(alpha = 0.9f),
            elevation = 12.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back",
                    fontSize = 34.sp,
                    color = Color(0xFF9C2BD4),
                    modifier = Modifier.padding(bottom = 40.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.people_icon),
                    contentDescription = "People Icon",
                    modifier = Modifier
                        .size(198.dp, 217.dp)
                        .padding(bottom = 20.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("School Email Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF6200EE),
                        unfocusedBorderColor = Color(0xFF6200EE)
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF6200EE),
                        unfocusedBorderColor = Color(0xFF6200EE)
                    )
                )


                Button(
                    onClick = {
                        // Launch a coroutine to call the suspend function
                        userViewModel.viewModelScope.launch {
                            val exists = userViewModel.getUserByEmailAndPassword(email, password)
                            // Show toast based on whether the user exists or not
                            val message = if (exists) "Login successful!" else "Invalid credentials."
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF48B76D)),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("register")
                    },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF757575)),
                    shape = RoundedCornerShape(8.dp) // Add rounded corners for a better look
                ) {
                    Text(
                        text = "Register Account",
                        color = Color.White, // Make sure the text color contrasts well with the button background
                                          )
                }

            }
        }

        // Observe the current user and navigate on success
        LaunchedEffect(currentUser) {
            if (currentUser != null) {
                d("UserViewModel","Login screen ${currentUser}")
                userViewModel.saveUserSession(currentUser!!)
                navController.navigate("post") {
                    popUpTo("login") { inclusive = true }
                    Toast.makeText(context, "Login Scucessful", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}