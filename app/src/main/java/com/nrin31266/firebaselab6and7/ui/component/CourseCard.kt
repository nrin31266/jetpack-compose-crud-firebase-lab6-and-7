package com.nrin31266.firebaselab6and7.ui.component

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nrin31266.firebaselab6and7.data.model.Course

import coil.compose.AsyncImage

@Composable
@Preview
fun CourseCard(
    course: Course = Course("1", "Kotlin Basics", "4 weeks", "Learn Kotlin from scratch", imageUrl = "https://developer.android.com/images/brand/Android_Robot.png"),
    handleClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { handleClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            course.imageUrl?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Course Image",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .shadow(2.dp, RoundedCornerShape(6.dp))
                        .background(Color.LightGray, shape = RoundedCornerShape(6.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(6.dp))
                        .weight(1f)
                        .fillMaxWidth(0.3f) // chiếm khoảng 30% chiều rộng
                )
            }

            Column(
                modifier = Modifier
                    .weight(3f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = course.name,
                    color = Color(90, 173, 58),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = course.duration,
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = course.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    maxLines = 2
                )
            }
        }
    }
}
