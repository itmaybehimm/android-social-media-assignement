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
import com.example.socialmedia.R

@Composable
fun RegisterScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                modifier = Modifier
                    .padding(bottom = 40.dp)
            )

            // Full Name TextField
            TextField(
                value = "",
                onValueChange = {},
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
                value = "",
                onValueChange = {},
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
                value = "",
                onValueChange = {},
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
                value = "",
                onValueChange = {},
                placeholder = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            )

            // Register Button
            Button(
                onClick = { /* Handle register click */ },
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