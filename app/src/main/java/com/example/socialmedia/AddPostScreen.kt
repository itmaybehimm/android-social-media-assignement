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
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.socialmedia.data.viewmodel.UserViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun AddPostScreen(navController: NavController, postViewModel: PostViewModel = viewModel(), userViewModel: UserViewModel = viewModel()) {
    var postText by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var localImagePath by remember { mutableStateOf<String?>(null) }
    var imageSelectionError by remember { mutableStateOf<String?>(null) }

    val currentUser by userViewModel.currentUser.observeAsState()
    val context = LocalContext.current

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri ->
            // Save image to local directory
            localImagePath = saveImageToLocalDirectory(context, imageUri)
            selectedImageUri = imageUri
            imageSelectionError = if (localImagePath == null) "Failed to save image" else null
        } ?: run {
            imageSelectionError = "Failed to select image"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Post",
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
                onClick = {
                    imagePickerLauncher.launch("image/*")
                },
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
                    if (postText.isNotEmpty() && localImagePath != null) {
                        val newPost = Post(
                            content = postText,
                            imageUrl = localImagePath, // Store the local path or use a file provider for secure access
                            userId = currentUser!!.id
                        )
                        postViewModel.addPost(newPost)
                        navController.popBackStack() // Navigate back after submission
                    } else if (postText.isEmpty()) {
                        imageSelectionError = "Please enter some content"
                    } else {
                        imageSelectionError = "Please select an image"
                    }
                },
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

fun saveImageToLocalDirectory(context: Context, imageUri: Uri): String? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
    val file = File(context.filesDir, "image_${System.currentTimeMillis()}.jpg")
    return try {
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        inputStream?.close()
    }
}