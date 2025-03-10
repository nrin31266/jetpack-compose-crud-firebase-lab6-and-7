package com.nrin31266.firebaselab6and7.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nrin31266.firebaselab6and7.data.model.Course
import com.nrin31266.firebaselab6and7.nav.Routes
import com.nrin31266.firebaselab6and7.ui.viewmodel.CourseViewModel

@Composable
fun CourseDetailScreen(
    navController: NavController,
    courseId: String,
    courseViewModel: CourseViewModel
) {

    val isLoading by courseViewModel.isLoading.collectAsState()
    val isLoadingButton by courseViewModel.isLoadingButton.collectAsState()
    var courseName: String by remember { mutableStateOf("") }
    var courseDescription: String by remember { mutableStateOf("") }
    var courseDuration: String by remember { mutableStateOf("") }


    val context = LocalContext.current
    LaunchedEffect(Unit) {
        courseViewModel.getCourseById(courseId, context, { data: Course ->
            run {
                courseName = data.name
                courseDescription = data.description
                courseDuration = data.description
            }
        })

    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text("Course ID: ${courseId}")

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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            var course = Course(
                                name = courseName,
                                duration = courseDuration,
                                description = courseDescription
                            )
                            courseViewModel.updateCourse(courseId ,course, context)

                        },
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(top = 20.dp),
                        enabled = !isLoadingButton
                    ) {
                        Text("Update")
                    }

                    Button(
                        onClick = {
                           courseViewModel.deleteCourse(courseId, context, {
                               navController.navigate(Routes.COURSE_LIST)
                           })


                        }, modifier = Modifier.padding(top = 20.dp), enabled = !isLoadingButton,
                         colors = ButtonColors(
                            contentColor = Color.White, containerColor = Color.Red, disabledContentColor = Color.White, disabledContainerColor = Color.Gray
                        )
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}