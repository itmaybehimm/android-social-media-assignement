package com.example.socialmedia

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.socialmedia.data.model.Post
import com.example.socialmedia.data.viewmodel.PostViewModel
import com.example.socialmedia.R
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

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
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var localImagePath by remember { mutableStateOf<String?>(null) }
    var imageSelectionError by remember { mutableStateOf<String?>(null) }

    // Activity result launcher for picking images
    val context = LocalContext.current
    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                // Save image to local directory
                localImagePath = saveImageToLocalDirectory(context, imageUri)
                selectedImageUri = uri
                imageSelectionError = if (localImagePath == null) "Failed to save image" else null
            } ?: run {
                imageSelectionError = "Failed to select image"
            }
        }

    // Initialize state variables if the post is available
    LaunchedEffect(post) {
        post?.let {
            postText = it.content
            // Initialize selectedImageUri and localImagePath if there's an image associated with the post
            selectedImageUri = it.imageUrl?.let { url -> Uri.parse(url) }
            localImagePath = it.imageUrl // Assuming the imageUrl is the local path
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

            // Display selected image if available
            localImagePath?.let { imagePath ->
                val imageBitmap: Bitmap? = BitmapFactory.decodeFile(imagePath)
                imageBitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
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
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to add photo
            Button(
                onClick = { imagePickerLauncher.launch("image/*") },
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

            // Show error message if image selection fails
            imageSelectionError?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Submit button
            Button(
                onClick = {
                    // Update the existing Post and submit it using the ViewModel
                    val updatedPost = post?.copy(
                        content = postText,
                        imageUrl = localImagePath // Update with actual image handling logic
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
