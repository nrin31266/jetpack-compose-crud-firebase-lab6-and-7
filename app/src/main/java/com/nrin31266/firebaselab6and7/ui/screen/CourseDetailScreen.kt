package com.nrin31266.firebaselab6and7.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.nrin31266.firebaselab6and7.R
import com.nrin31266.firebaselab6and7.data.model.Course
import com.nrin31266.firebaselab6and7.ui.viewmodel.CourseViewModel
import androidx.navigation.NavController

@Composable
fun CourseDetailScreen(
    navController: NavController,
    courseId: String,
    courseViewModel: CourseViewModel
) {
    val isLoading by courseViewModel.isLoading.collectAsState()
    val isLoadingButton by courseViewModel.isLoadingButton.collectAsState()
    var courseName by remember { mutableStateOf("") }
    var courseDescription by remember { mutableStateOf("") }
    var courseDuration by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf<String?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    LaunchedEffect(Unit) {
        courseViewModel.getCourseById(courseId, context) { data ->
            courseName = data.name
            courseDescription = data.description
            courseDuration = data.duration // bạn viết nhầm courseDuration = description rồi nhé
            imageUrl = data.imageUrl
        }
    }

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Course ID: $courseId")

                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .size(200.dp)
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        imageUri != null -> {
                            Image(
                                painter = rememberAsyncImagePainter(imageUri),
                                contentDescription = "Selected Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        !imageUrl.isNullOrEmpty() -> {
                            Image(
                                painter = rememberAsyncImagePainter(imageUrl),
                                contentDescription = "Course Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            Image(
                                painter = painterResource(id = R.drawable.add_image_icon),
                                contentDescription = "Add Image",
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = courseName,
                    onValueChange = { courseName = it },
                    label = { Text("Course Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = courseDuration,
                    onValueChange = { courseDuration = it },
                    label = { Text("Course Duration") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = courseDescription,
                    onValueChange = { courseDescription = it },
                    label = { Text("Course Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            val course = Course(
                                name = courseName,
                                duration = courseDuration,
                                description = courseDescription,
                                imageUrl = imageUrl
                            )
                            if (imageUri != null) {
                                course.uri = imageUri
                            }
                            courseViewModel.updateCourse(courseId, course, context)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 20.dp),
                        enabled = !isLoadingButton
                    ) {
                        Text("Update")
                    }

                    Button(
                        onClick = {
                            courseViewModel.deleteCourse(courseId, context) {
                                navController.popBackStack()
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 20.dp),
                        enabled = !isLoadingButton,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
