package com.nrin31266.firebaselab6and7.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.nrin31266.firebaselab6and7.R
import com.nrin31266.firebaselab6and7.data.model.Course
import com.nrin31266.firebaselab6and7.ui.viewmodel.CourseViewModel

@Composable
fun AddCourseScreen(navController: NavController, courseViewModel: CourseViewModel) {
    val context = LocalContext.current
    var courseName: String by remember { mutableStateOf("") }
    var courseDescription: String by remember { mutableStateOf("") }
    var courseDuration: String by remember { mutableStateOf("") }
    val isLoading by courseViewModel.isLoadingButton.collectAsState()
    var courseUri by remember { mutableStateOf<Uri?>(null) }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            courseUri = uri
        }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .size(200.dp)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (courseUri == null) {
                        Image(
                            painter = painterResource(id = R.drawable.add_image_icon),
                            contentDescription = "",
                            Modifier.size(100.dp)
                        )
                    } else {
                        courseUri?.let {
                            Image(
                                painter = rememberAsyncImagePainter(it),
                                contentDescription = "Selected Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                }
            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Course Name")
                },
                value = courseName,
                onValueChange = {
                    courseName = it
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Course Duration")
                },
                value = courseDuration,
                onValueChange = {
                    courseDuration = it
                }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Course Description")
                },
                value = courseDescription,
                onValueChange = {
                    courseDescription = it
                }
            )

            Button(
                onClick = {
                    var course = Course(
                        name = courseName,
                        duration = courseDuration,
                        description = courseDescription,

                        )
                    if (courseUri != null) {
                        course.uri = courseUri
                    }
                    courseViewModel.addCourse(course, context , {
                        navController.popBackStack()

                    })


                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp), enabled = !isLoading
            ) {
                Text("Add Course")
            }
        }
    }
}