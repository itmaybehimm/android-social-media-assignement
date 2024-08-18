package com.example.socialmedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavController
import com.example.socialmedia.R

@Composable
fun AdminScreen(navController: NavController) {
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
            backgroundColor = MaterialTheme.colors.surface,
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
                    text = "Admin Panel",
                    fontSize = 34.sp,
                    color = Color(0xFF9C2BD4),
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text("Student ID") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF6200EE),
                        unfocusedBorderColor = Color(0xFF6200EE)
                    )
                )

                Text(
                    text = "Student List",
                    fontSize = 34.sp,
                    color = Color(0xFF9C2BD4),
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    elevation = 4.dp,
                    shape = androidx.compose.material3.MaterialTheme.shapes.medium,
                    backgroundColor = Color.White
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Student Name",
                            fontSize = 16.sp,
                            color = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Student ID",
                            fontSize = 14.sp,
                            color = Color(0xFF777777)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { /* Handle Edit Click */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
                            ) {
                                Text(text = "Edit", color = Color.White)
                            }
                            Button(
                                onClick = { /* Handle Delete Click */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                            ) {
                                Text(text = "Delete", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
