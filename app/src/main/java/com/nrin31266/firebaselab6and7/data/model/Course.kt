package com.nrin31266.firebaselab6and7.data.model

import com.google.firebase.firestore.Exclude
import org.jetbrains.annotations.Async.Execute

data class Course(
    var courseId:String = "",
    var name: String="",
    var description: String = "",
    var duration:String = "",
)
