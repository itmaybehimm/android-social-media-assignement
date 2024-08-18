package com.example.socialmedia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable

@Composable
fun SocialMediaApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("admin") { AdminScreen(navController) }
        composable("post") { PostScreen(navController) }
        composable("addpost") { AddPostScreen(navController) }

    }
}