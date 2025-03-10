package com.nrin31266.firebaselab6and7.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nrin31266.firebaselab6and7.ui.screen.AddCourseScreen
import com.nrin31266.firebaselab6and7.ui.screen.CourseDetailScreen
import com.nrin31266.firebaselab6and7.ui.screen.CourseListScreen
import com.nrin31266.firebaselab6and7.ui.screen.HomeScreen
import com.nrin31266.firebaselab6and7.ui.viewmodel.CourseViewModel

@Composable
fun NavGraph (navController: NavHostController){

    val courseViewModel: CourseViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.HOME){
        composable(Routes.HOME){
            HomeScreen(navController)
        }

        composable(Routes.ADD_COURSE){
            AddCourseScreen(navController, courseViewModel)
        }

        composable(Routes.COURSE_LIST){
            CourseListScreen(navController, courseViewModel)
        }

        composable(
            route = "${Routes.COURSE_DETAIL}/{courseId}",
            arguments = listOf(navArgument("courseId") { type = NavType.StringType })
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId")
            CourseDetailScreen(navController, courseId!!, courseViewModel)
        }
    }
}