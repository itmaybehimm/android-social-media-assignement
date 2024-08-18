package com.example.socialmedia.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.isSystemInDarkTheme

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9B4F5B),
    secondary = Color(0xFF4D4D4D),
    tertiary = Color(0xFFCE93D8)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6A1B9A),
    secondary = Color(0xFF6D6E71),
    tertiary = Color(0xFFEC407A)
)

@Composable
fun SocialMediaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}