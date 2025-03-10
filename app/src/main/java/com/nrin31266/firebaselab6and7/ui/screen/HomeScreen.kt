package com.nrin31266.firebaselab6and7.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nrin31266.firebaselab6and7.nav.Routes

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
            horizontalAlignment = (Alignment.CenterHorizontally),
            verticalArrangement = (Arrangement.spacedBy(10.dp))
        ) {
            Button(onClick = {
                navController.navigate(Routes.ADD_COURSE)
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Course")
            }
            OutlinedButton (onClick = {
                navController.navigate(Routes.COURSE_LIST)
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Course List")
            }

        }
    }
}