package com.example.socialmedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.example.socialmedia.data.viewmodel.CommentViewModel
import com.example.socialmedia.data.viewmodel.PostViewModel
import com.example.socialmedia.data.viewmodel.UserViewModel

@Composable
fun PostScreen(
    navController: NavController,
    postViewModel: PostViewModel = viewModel(),
    commentViewModel: CommentViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel()
) {
    // Collect state from ViewModel
    val posts by postViewModel.allPosts.collectAsState(initial = emptyList())

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
                Log.d("PostItem", "Comments collected: ${comments.joinToString { it.content }}")

                PostItem(
                    post = post,
                    onLike = { postViewModel.increaseLikeCount(post.id) },
                    onDislike = { postViewModel.increaseDislikeCount(post.id) },
                    commentViewModel = commentViewModel,
                    userViewModel = userViewModel
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
                painter = painterResource(id = R.drawable.baseline_add_24), // Replace with your add icon
                contentDescription = "Add Post"
            )
        }
    }
}
