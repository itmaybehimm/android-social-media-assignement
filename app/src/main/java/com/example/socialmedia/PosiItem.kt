package com.example.socialmedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.socialmedia.data.model.Comment
import com.example.socialmedia.data.model.Post
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.viewmodel.CommentViewModel
import com.example.socialmedia.data.viewmodel.UserViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.permissions.isGranted

@Composable
fun PostItem(
    post: Post,
    commentViewModel: CommentViewModel,
    userViewModel: UserViewModel,
    currentUser: User,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val comments by commentViewModel.getCommentsByPostId(post.id).collectAsState()
    val commentsPerPage = 4
    var currentPage by remember { mutableStateOf(0) }
    val paginatedComments = comments
        .drop(currentPage * commentsPerPage)
        .take(commentsPerPage)
    val user by userViewModel.getUserById(post.userId).observeAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var commentToDelete by remember { mutableStateOf<Comment?>(null) }

    if (showDeleteDialog && commentToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete this comment?") },
            confirmButton = {
                Button(onClick = {
                    Log.d("PostItem", "Deleting comment with ID: ${commentToDelete?.id}") // Debug log
                    showDeleteDialog = false
                    commentToDelete?.let { comment ->
                        commentViewModel.deleteComment(comment)
                    }
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("No")
                }
            }
        )
    }

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
                text = user?.fullName ?: "Unknown User",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = post.content,
                fontSize = 16.sp,
                color = Color(0xFF333333),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            post.imageUrl?.let { imageUrl ->
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build()
                )

                Image(
                    painter = painter,
                    contentDescription = "Post Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            var commentText by remember { mutableStateOf(TextFieldValue()) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text(text = "Add a comment") }
                )

                Button(
                    onClick = {
                        if (commentText.text.isBlank()) {
                            Toast.makeText(context, "Comment can't be empty", Toast.LENGTH_SHORT).show()
                        } else {
                            val comment = Comment(
                                postId = post.id,
                                userId = currentUser.id,
                                content = commentText.text
                            )
                            commentViewModel.insertComment(comment)
                            commentText = TextFieldValue() // Clear the text field
                        }
                    },
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

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Set a fixed height for the box
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(paginatedComments) { comment ->
                        val commentUser by userViewModel.getUserById(comment.userId).observeAsState()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${commentUser?.fullName ?: "Unknown User"} : ${comment.content}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                modifier = Modifier.weight(1f)
                            )
                            if (currentUser.id == comment.userId || currentUser.role == "admin") {
                                IconButton(onClick = {
                                    Log.d("PostItem", "Preparing to delete comment with ID: ${comment.id}") // Debug log
                                    commentToDelete = comment
                                    showDeleteDialog = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Delete Comment",
                                        tint = Color.Red
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { if (currentPage > 0) currentPage-- },
                    enabled = currentPage > 0,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF48B76D))
                ) {
                    Text(text = "Previous", fontSize = 14.sp, color = Color.White)
                }

                Button(
                    onClick = { if ((currentPage + 1) * commentsPerPage < comments.size) currentPage++ },
                    enabled = (currentPage + 1) * commentsPerPage < comments.size,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF48B76D))
                ) {
                    Text(text = "Next", fontSize = 14.sp, color = Color.White)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = onLike) {
                    Icon(
                        imageVector = Icons.Filled.ThumbUp,
                        contentDescription = "Like",
                        tint = Color(0xFF48B76D)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "${post.likeCount}", fontSize = 14.sp, color = Color.Black)

                Spacer(modifier = Modifier.width(16.dp))

                IconButton(onClick = onDislike) {
                    Icon(
                        imageVector = Icons.Filled.ThumbDown,
                        contentDescription = "Dislike",
                        tint = Color(0xFFB74B4B)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(text = "${post.dislikeCount}", fontSize = 14.sp, color = Color.Black)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (currentUser.id == post.userId || currentUser.role == "admin") {
                    IconButton(onClick = onEdit) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xFF9C2BD4)
                        )
                    }

                    IconButton(onClick = {
                        Log.d("PostItem", "Preparing to delete post with ID: ${post.id}") // Debug log
                        onDelete()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}
