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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.socialmedia.R
import com.example.socialmedia.StudentItem
import com.example.socialmedia.data.model.User
import com.example.socialmedia.data.viewmodel.UserViewModel

@Composable
fun AdminScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    // Observe the list of students
    val students by userViewModel.allStudents.collectAsState(initial = emptyList())
    var currentPage by remember { mutableStateOf(1) }
    val studentsPerPage = 2
    var searchQuery by remember { mutableStateOf("") }

    // Filter students based on search query
    val filteredStudents = students.filter {
        it.email.contains(searchQuery, ignoreCase = true)
    }

    // Pagination logic
    val totalPages = (filteredStudents.size + studentsPerPage - 1) / studentsPerPage
    val paginatedStudents = filteredStudents.chunked(studentsPerPage).getOrNull(currentPage - 1) ?: emptyList()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_a),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .align(Alignment.Center),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 12.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Admin Panel",
                    fontSize = 34.sp,
                    color = Color(0xFF9C2BD4),
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                Text(
                    text = "Student List",
                    fontSize = 28.sp,
                    color = Color(0xFF9C2BD4),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Search by email") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(paginatedStudents) { student ->
                        StudentItem(
                            studentName = student.fullName,
                            studentId = student.id.toString(),
                            onEdit = {
                                navController.navigate("studentedit/${student.id}")
                            },
                            onDelete = { userViewModel.deleteUser(student) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { if (currentPage > 1) currentPage-- },
                        enabled = currentPage > 1
                    ) {
                        Text(text = "Previous")
                    }

                    Text(
                        text = "Page $currentPage of $totalPages",
                        fontSize = 16.sp,
                        color = Color(0xFF9C2BD4),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Button(
                        onClick = { if (currentPage < totalPages) currentPage++ },
                        enabled = currentPage < totalPages
                    ) {
                        Text(text = "Next")
                    }
                }
            }
        }
    }
}
