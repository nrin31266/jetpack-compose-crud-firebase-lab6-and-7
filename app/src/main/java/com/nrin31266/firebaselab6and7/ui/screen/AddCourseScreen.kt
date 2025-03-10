package com.nrin31266.firebaselab6and7.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nrin31266.firebaselab6and7.data.model.Course
import com.nrin31266.firebaselab6and7.ui.viewmodel.CourseViewModel

@Composable
fun AddCourseScreen(navController: NavController, courseViewModel: CourseViewModel){
    val context = LocalContext.current
    var courseName :String by remember { mutableStateOf("") }
    var courseDescription :String by remember { mutableStateOf("") }
    var courseDuration :String by remember { mutableStateOf("") }
    val isLoading by courseViewModel.isLoading.collectAsState()



    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column (modifier = Modifier.fillMaxWidth().padding(start = 5.dp, end = 5.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)){
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

            Button(onClick = {
                var course = Course(
                    name = courseName,
                    duration = courseDuration,
                    description = courseDescription
                )
                courseViewModel.addCourse(course, context)
                navController.popBackStack()

            }, modifier = Modifier.fillMaxWidth().padding(top = 20.dp), enabled = !isLoading) {
                Text("Add Course")
            }
        }
    }
}