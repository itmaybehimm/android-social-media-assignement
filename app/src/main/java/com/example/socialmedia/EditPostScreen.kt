package com.example.socialmedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.socialmedia.data.model.Post
import com.example.socialmedia.data.viewmodel.PostViewModel
import com.example.socialmedia.R

@Composable
fun EditPostScreen(
    navController: NavController,
    postId: Int, // The ID of the post to be edited
    postViewModel: PostViewModel = viewModel()
) {
    // Retrieve the post from the ViewModel
    val post by postViewModel.getPostById(postId).collectAsState(initial = null)

    // State variables for editing
    var postText by remember { mutableStateOf("") }
    var selectedImage by remember { mutableStateOf<Int?>(null) }

    // Initialize state variables if the post is available
    LaunchedEffect(post) {
        post?.let {
            postText = it.content
            // Initialize selectedImage if there's an image associated with the post
            // selectedImage = it.imageResId // Update with actual image handling logic
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
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
                text = "Edit Post",
                fontSize = 28.sp,
                color = Color(0xFF9C2BD4),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Text field for post content
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

            // Placeholder for image
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

            // Button to add photo
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

            // Submit button
            Button(
                onClick = {
                    // Update the existing Post and submit it using the ViewModel
                    val updatedPost = post?.copy(
                        content = postText,
                        // imageResId = selectedImage ?: post?.imageResId // Update with actual image handling logic
                    )
                    updatedPost?.let {
                        postViewModel.updatePost(it)
                        navController.popBackStack() // Navigate back after submission
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C2BD4))
            ) {
                Text(
                    text = "Update",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp)
                )
            }
        }
    }
}
