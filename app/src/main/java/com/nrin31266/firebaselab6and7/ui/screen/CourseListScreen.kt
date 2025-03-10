package com.nrin31266.firebaselab6and7.ui.screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nrin31266.firebaselab6and7.nav.Routes
import com.nrin31266.firebaselab6and7.ui.component.CourseCard
import com.nrin31266.firebaselab6and7.ui.viewmodel.CourseViewModel

@Composable
fun CourseListScreen(navController: NavController, courseViewModel: CourseViewModel){

    val isLoading by courseViewModel.isLoading.collectAsState()
    LaunchedEffect(Unit) {
        courseViewModel.getCourses()

    }

    val listData by courseViewModel.courses.collectAsState()


    if(isLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    } else{
        Column (
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            listData.map { course ->
                CourseCard(course, {
                    navController.navigate("${Routes.COURSE_DETAIL}/${course.courseId}")
                })
            }
        }

    }

}