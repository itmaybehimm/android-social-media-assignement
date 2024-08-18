package com.example.socialmedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.socialmedia.R

@Composable
fun AddPostScreen(navController: NavController) {
    val playfairFontFamily = FontFamily(Font(R.font.playfair))
    var postText by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Int?>(null) }

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
                text = "Add post",
                fontSize = 28.sp,
                fontFamily = playfairFontFamily,
                color = Color(0xFF9C2BD4),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = postText,
                onValueChange = { postText = it },
                placeholder = { Text("What's on your mind?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                textStyle = TextStyle(color = Color.Black)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedImage != null) {
                Image(
                    painter = painterResource(id = selectedImage!!),
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Logic to select image */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = "Add Photo",
                    color = Color.Black,
                    style = TextStyle(fontSize = 16.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Logic to submit post */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C2BD4))
            ) {
                Text(
                    text = "Post",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }
    }
}