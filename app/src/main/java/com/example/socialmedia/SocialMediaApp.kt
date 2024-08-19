package com.example.socialmedia

import AdminScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.socialmedia.data.viewmodel.UserViewModel

@Composable
fun SocialMediaApp() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel() // Create ViewModel instance here

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("login") { LoginScreen(navController, userViewModel) }
        composable("register") { RegisterScreen(navController, userViewModel) }
        composable("admin") { AdminScreen(navController, userViewModel) }
        composable("post") { PostScreen(navController) }
        composable("addpost") { AddPostScreen(navController) }
        composable("editpost/{postId}") { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull()
            if (postId != null) {
                EditPostScreen(navController, postId)
            } else {
                // Handle error, maybe show a message or navigate back
            }
        }
        composable("studentedit/{studentId}") { backStackEntry ->
            val studentId = backStackEntry.arguments?.getString("studentId")?.toIntOrNull()
            if (studentId != null) {
                StudentEditScreen(studentId, userViewModel)
            } else {
                // Handle error, maybe show a message or navigate back
            }
        }
    }
}
