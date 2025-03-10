package com.nrin31266.firebaselab6and7.ui.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.nrin31266.firebaselab6and7.data.model.Course
import com.nrin31266.firebaselab6and7.data.reponsitory.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CourseViewModel :ViewModel() {
    private val repository = CourseRepository()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private val _loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _loading

    private val _loadingButton = MutableStateFlow(false)
    val isLoadingButton: StateFlow<Boolean> = _loadingButton


    fun getCourses() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val courseList = repository.getCourses()
                _courses.value = courseList

            } catch (e: FirebaseException) {
                e.printStackTrace()
            }finally {
                _loading.value = false
            }
        }
    }

    fun addCourse (courseReq: Course, context: Context){
        _loadingButton.value = true
        viewModelScope.launch (Dispatchers.IO){
            try {
                val courseId = repository.addCourse(courseReq)
                withContext(Dispatchers.Main){
                    Toast.makeText(context, "Add Course Successfully With ID: $courseId", Toast.LENGTH_SHORT).show()
                }
            }catch (e: FirebaseException){
                withContext(Dispatchers.Main){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }finally {
                _loadingButton.value = false
            }

        }
    }


    fun updateCourse(courseId: String, course: Course, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingButton.value = true
            try {
                repository.updateCourse(courseId, course)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Course Updated Successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: FirebaseException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }finally {
                _loadingButton.value = false
            }
        }
    }


    fun deleteCourse(courseId: String, context: Context, back:()->Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            _loadingButton.value = true
            try {
                repository.deleteCourse(courseId)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Course Deleted Successfully", Toast.LENGTH_SHORT).show()
                    back()
                }
            } catch (e: FirebaseException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }finally {
                _loadingButton.value = false
            }
        }
    }

    fun getCourseById(courseId: String, context: Context, onResult: (data: Course)->Unit){
        _loading.value = true
        viewModelScope.launch (Dispatchers.IO){
            try {
                val course = repository.getCourseById(courseId)
                if (course == null){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Course Not Found With ID: "+ courseId, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        onResult(course)
                    }
                }

            }catch (e: FirebaseException){
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }finally {
                _loading.value = false
            }
        }
    }

}