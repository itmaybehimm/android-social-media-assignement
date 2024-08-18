package com.example.socialmedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.socialmedia.R

@Composable
fun MainScreen(navController: NavController) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Student App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.playfair)),
                color = Color(0xFF9C2BD4),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = "Please register or login",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = { navController.navigate("register") },
                modifier = Modifier
                    .width(200.dp)
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Register",
                    color = Color.White
                )
            }

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.width(200.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Copyright@2024",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.playfair)),
                color = Color.Black,
                modifier = Modifier.padding(top = 100.dp)
            )
        }
    }
}