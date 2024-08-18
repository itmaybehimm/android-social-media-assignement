package com.example.socialmedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.socialmedia.R

@Composable
fun ProfileScreen(navController: NavController) {
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Image
            Image(
                painter = painterResource(id = R.drawable.image_placeholder),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(top = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Username
            Text(
                text = "@Toa_Heftiba",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            // User stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "121.9k\nFollowers",
                    style = TextStyle(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                )
                // Add other stats here (e.g., Following, Likes) if needed
            }

            // Follow Button
            Button(
                onClick = { /* Handle follow click */ },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFACCECE)),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Follow")
            }

            // Tab Layout (Placeholder, customize as needed)
            TabRow(
                selectedTabIndex = 0,
                modifier = Modifier
                    .padding(top = 28.dp)
                    .fillMaxWidth()
            ) {
                // Add tabs here
            }

            // Photo Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                items(20) { index -> // Replace 20 with your data size
                    PhotoItem()
                }
            }
        }
    }
}

@Composable
fun PhotoItem() {
    // Replace with your photo item composable
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .background(Color.Gray) // Correct usage of background modifier
    ) {
        // Image or content here
    }
}