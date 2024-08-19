package com.example.socialmedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.navigation.NavController
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.viewmodel.UserViewModel
@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    var fullName by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_a),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Register",
                fontSize = 24.sp,
                color = Color(0xFF9C2BD4),
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Full Name TextField
            TextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text(text = "Full Name") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            )

            // Date of Birth TextField
            TextField(
                value = dateOfBirth,
                onValueChange = { dateOfBirth = it },
                placeholder = { Text(text = "Date of Birth") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            )

            // Secondary School Email Address TextField
            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Secondary School Email Address") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            )

            // Password TextField
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            )

            // Error Message Text
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Register Button
            Button(
                onClick = {
                    if (fullName.isNotEmpty() && dateOfBirth.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        val user = User(fullName = fullName, email = email, dateOfBirth = dateOfBirth, password = password)
                        userViewModel.insertUser(
                            user,
                            onSuccess = {
                                // Ensure this runs on the main thread
                                navController.navigate("login") // Navigate to login or another screen
                            },
                            onError = { message ->
                                // Ensure this runs on the main thread
                                errorMessage = "Invalid parameters"
                            }
                        )
                    } else {
                        errorMessage = "Please fill in all fields"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(48.dp)
            ) {
                Text(text = "Register")
            }
        }
    }
}
