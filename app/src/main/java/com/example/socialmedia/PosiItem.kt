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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.socialmedia.data.model.Comment
import com.example.socialmedia.data.model.Post
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.viewmodel.CommentViewModel
import com.example.socialmedia.data.viewmodel.UserViewModel
@Composable
fun PostItem(
    post: Post,
    commentViewModel: CommentViewModel,
    userViewModel: UserViewModel,
    currentUser: User,
    onLike: () -> Unit,
    onDislike: () -> Unit,
    onEdit: () -> Unit // Add this parameter
) {
    val comments by commentViewModel.getCommentsByPostId(post.id).collectAsState()
    val commentsPerPage = 4
    var currentPage by remember { mutableStateOf(0) }

    // Calculate the paginated comments
    val paginatedComments = comments
        .drop(currentPage * commentsPerPage)
        .take(commentsPerPage)

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
                text = post.content,
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
                        val comment = Comment(
                            postId = post.id,
                            userId = currentUser.id, // Use currentUser ID
                            content = commentText.text
                        )
                        commentViewModel.insertComment(comment)
                        commentText = TextFieldValue() // Clear the text field
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
                        val user by userViewModel.getUserById(comment.userId).observeAsState()
                        Text(
                            text = "${user?.fullName ?: "Unknown User"} : ${comment.content}",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
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
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onLike,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF48B76D))
                ) {
                    Text(text = "Like (${post.likeCount})", fontSize = 14.sp, color = Color.White)
                }

                Button(
                    onClick = onDislike,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFB74B4B))
                ) {
                    Text(text = "Dislike (${post.dislikeCount})", fontSize = 14.sp, color = Color.White)
                }

                // Show edit button if the current user is the post author
                if (currentUser.id == post.userId) {
                    Button(
                        onClick = onEdit,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C2BD4))
                    ) {
                        Text(text = "Edit", fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }
    }
}
