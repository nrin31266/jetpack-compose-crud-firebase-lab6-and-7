package com.nrin31266.firebaselab6and7.data.model

import android.net.Uri
import com.google.firebase.firestore.Exclude
import org.jetbrains.annotations.Async.Execute

data class Course(
    var courseId:String = "",
    var name: String="",
    var description: String = "",
    var duration:String = "",
    @Exclude var uri: Uri? = null,
    var imageUrl: String = ""
)
