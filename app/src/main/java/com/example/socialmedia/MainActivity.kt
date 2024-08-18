package com.example.socialmedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.socialmedia.ui.theme.SocialMediaTheme // Adjust according to your theme setup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaTheme { // Apply your theme here
                Surface(color = MaterialTheme.colorScheme.background) {
                    SocialMediaApp()
                }
            }
        }
    }
}