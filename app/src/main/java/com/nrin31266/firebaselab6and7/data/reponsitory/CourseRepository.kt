package com.nrin31266.firebaselab6and7.data.reponsitory

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nrin31266.firebaselab6and7.data.model.Course
import kotlinx.coroutines.tasks.await

class CourseRepository{
    private val db = Firebase.firestore

    suspend fun getCourses() : List<Course>{
        val firestoreRef = db.collection("course")
        val snapshot = firestoreRef.get().await()
        return snapshot.toObjects(Course::class.java)
    }

    suspend fun addCourse(course: Course) : String{
        val firestoreRef = db.collection("course").document()
        val snapshot = firestoreRef.set(course.copy(courseId = firestoreRef.id)).await()
        return firestoreRef.id
    }

    suspend fun updateCourse( courseId: String, course: Course) {
        val firestoreRef = db.collection("course").document(courseId)
        val snapshot = firestoreRef.set(course.copy(courseId = courseId)).await()
    }

    suspend fun deleteCourse( courseId: String){
        val firestoreRef = db.collection("course").document(courseId)
        firestoreRef.delete().await()
    }

    suspend fun getCourseById(courseId: String): Course? {
        val firebaseRef = db.collection("course").document(courseId)
        val snapshot = firebaseRef.get().await()
        return snapshot.toObject(Course::class.java)
    }
}