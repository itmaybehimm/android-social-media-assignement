package com.example.socialmedia

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.R
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.viewmodel.UserViewModel

@Composable
fun StudentEditScreen(navController: NavController,studentId: Int, userViewModel: UserViewModel = viewModel()) {
    val playfairFontFamily = FontFamily(Font(R.font.playfair))
    val student by userViewModel.getUserById(studentId).observeAsState()
    var fullName by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val backgroundImage: Painter = painterResource(id = R.drawable.bg_a)
    val context = LocalContext.current

    // Update state when student data changes
    LaunchedEffect(student) {
        student?.let {
            fullName = it.fullName
            dob = it.dateOfBirth
            email = it.email
            // Do not auto-fill password for security reasons
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent) // Set this to transparent
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Edit detail",
                fontSize = 24.sp,
                fontFamily = playfairFontFamily,
                color = Color(0xFF9C2BD4),
                modifier = Modifier.padding(bottom = 40.dp)
            )

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("Full Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = dob,
                onValueChange = { dob = it },
                placeholder = { Text("Date of Birth") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Secondary School Email Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Button(
                onClick = {
                    if (fullName.isEmpty() || dob.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                    } else {
                        userViewModel.updateUser(
                            User(id = studentId, fullName = fullName, dateOfBirth = dob, email = email, password = password, createdAt = student!!.createdAt, updatedAt = getCurrentDateTime()),
                            onSuccess = {
                                Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show()
                                navController.navigate("post")
                            },
                            onError = { errorMessage ->
                                Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9C2BD4))
            ) {
                Text(text = "Confirm", color = Color.White)
            }
        }
    }
}
