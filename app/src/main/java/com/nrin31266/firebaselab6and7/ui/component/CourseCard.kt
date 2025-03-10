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

@Composable
@Preview
fun CourseCard(
    course: Course = Course("1", "C2", "acasdsa", "dsadsadsa"),
    handleClick: () -> Unit = {


    }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { handleClick() },
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = course.name,
                color = Color(90, 173, 58),
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = course.duration,
                fontSize = 24.sp,
            )
            Text(
                text = course.description,
                fontSize = 24.sp,
            )
        }
    }
}