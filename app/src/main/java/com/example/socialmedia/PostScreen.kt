package com.example.socialmedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.data.model.Post
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.viewmodel.CommentViewModel
import com.example.socialmedia.data.viewmodel.PostViewModel
import com.example.socialmedia.data.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun PostScreen(
    navController: NavController,
    postViewModel: PostViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()
) {
    // Collect state from ViewModel
    val posts by postViewModel.allPosts.collectAsState(initial = emptyList())

    // Observe current user
    val currentUser by userViewModel.currentUser.observeAsState()

    // State for tracking navigation
    var navigateToLogin by remember { mutableStateOf(false) }

    // State for showing confirmation dialog
    var showConfirmDialog by remember { mutableStateOf(false) }
    var postToDelete by remember { mutableStateOf<Post?>(null) }

    // Effect to handle navigation
    LaunchedEffect(navigateToLogin) {
        if (navigateToLogin) {
            navController.navigate("main") {
                // Clear the back stack to prevent returning to PostScreen
                popUpTo("main") { inclusive = true }
            }
            navigateToLogin = false // Reset the state
        }
    }

    // Show content only if user is logged in
    currentUser?.let { currentUser ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Background image that fills the entire box
            Image(
                painter = painterResource(id = R.drawable.bg_a),
                contentDescription = "Background",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )

            // List of posts
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Text(
                        text = "Post List",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9C2BD4),
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }

                items(posts) { post ->
                    // Fetch comments for this post
                    val comments by commentViewModel.getCommentsByPostId(post.id).collectAsState(initial = emptyList())

                    PostItem(
                        post = post,
                        postViewModel = postViewModel,
                        commentViewModel = commentViewModel,
                        userViewModel = userViewModel,
                        currentUser = currentUser, // Pass the current user to PostItem
                        onEdit = {
                            navController.navigate("editpost/${post.id}")
                        },
                        onDelete = {
                            // Show the confirmation dialog
                            postToDelete = post
                            showConfirmDialog = true
                        }
                    )
                }
            }

            // FloatingActionButton for navigating to AddPostScreen
            FloatingActionButton(
                onClick = { navController.navigate("addpost") },
                backgroundColor = Color(0xFF9C2BD4),
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Add Post"
                )
            }

            // Logout button
            Button(
                onClick = {
                    userViewModel.logout {
                        // Trigger navigation to login screen after logout completes
                        navigateToLogin = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C2BD4))
            ) {
                Text(text = "Logout", color = Color.White)
            }

            // Admin button if the user is an admin
            if (currentUser.role == "admin") {
                FloatingActionButton(
                    onClick = { navController.navigate("admin") },
                    backgroundColor = Color(0xFF9C2BD4),
                    contentColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_admin_panel_settings_24), // Replace with your desired admin icon
                        contentDescription = "Navigate to Admin"
                    )
                }

            }
            else{
                FloatingActionButton(
                    onClick = { navController.navigate("studentedit/${currentUser.id}") },
                    backgroundColor = Color(0xFF9C2BD4),
                    contentColor = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_stat_name), // Replace with your desired admin icon
                        contentDescription = "Navigate to Admin"
                    )
                }
            }

            // Show confirmation dialog if required
            if (showConfirmDialog && postToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showConfirmDialog = false },
                    title = { Text("Delete Post") },
                    text = { Text("Are you sure you want to delete this post?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                postToDelete?.let {
                                    postViewModel.deletePost(it)
                                    postToDelete = null
                                }
                                showConfirmDialog = false
                            }
                        ) {
                            Text("Delete")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showConfirmDialog = false
                                postToDelete = null
                            }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
