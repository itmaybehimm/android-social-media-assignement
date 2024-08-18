package com.example.socialmedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia.R

@Composable
fun PostItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 4.dp,
        backgroundColor = Color.White,
        shape = MaterialTheme.shapes.medium.copy(CornerSize(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "This is the content of the blog post.",
                fontSize = 16.sp,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.bg_a),
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val commentText = remember { mutableStateOf(TextFieldValue()) }

                    OutlinedTextField(
                        value = commentText.value,
                        onValueChange = { commentText.value = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        placeholder = { Text(text = "Add a comment") }
                    )

                    Button(
                        onClick = { /* Handle submit comment */ },
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Submit Comment",
                            fontSize = 10.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

                Text(
                    text = "Comments:",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}