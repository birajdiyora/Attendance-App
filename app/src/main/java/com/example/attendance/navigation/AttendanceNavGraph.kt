package com.example.attendance.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.attendance.data.AttendanceRepository
import com.example.attendance.ui.screen.aboutUsScreen.AboutUsScreen
import com.example.attendance.ui.screen.addNameScreen.AddNameScreen
import com.example.attendance.ui.screen.homeScreen.HomeScreen
import com.example.attendance.ui.screen.subjectsScreen.SubjectsScreen
import com.example.attendance.ui.screen.viewAttendanceScreen.ViewAttendanceScreen
import com.example.attendance.ui.screen.viewSubjectAttendanceScreen.ViewSubjectAttendanceScreen
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AttendanceNavGraph (
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startingScreen : String
) {
    NavHost(
        navController = navController,
        startDestination =  startingScreen)
    {
        composable(
            route = AttendanceScreen.HomeScreen.route
        ){
            HomeScreen(
                navigateToSubjectScreen = {navController.navigate(AttendanceScreen.SubjectScreen.route)},
                navigateToViewAttendanceScreen = {navController.navigate(AttendanceScreen.ViewAttendanceScreen.route)},
                navigateToViewSubjectAttendanceScreen = {
                    navController.navigate("${AttendanceScreen.ViewSubjectAttendanceScreen.route}/${it.subjectId}/${it.subjectName}")
                },
                navigateToAboutUsScreen = {
                    navController.navigate(AttendanceScreen.AboutUsScreen.route)
                }
            )
        }
        composable(
            route = "${AttendanceScreen.ViewSubjectAttendanceScreen.route}/{subject_id}/{subject_name}",
            arguments = listOf(
                navArgument("subject_id"){
                    type = NavType.IntType
                },
                navArgument("subject_name"){
                    type = NavType.StringType
                }
            )
        ){
            ViewSubjectAttendanceScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = AttendanceScreen.ViewAttendanceScreen.route
        ){
            ViewAttendanceScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = AttendanceScreen.SubjectScreen.route
        ){
            SubjectsScreen(
                onNavigationBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = AttendanceScreen.AddNameScreen.route
        ){
            AddNameScreen(navigateToHomeScreen = {
                navController.navigate(route = AttendanceScreen.HomeScreen.route)
            })
        }
        composable(route = AttendanceScreen.AboutUsScreen.route){
            AboutUsScreen(
                onNavigationBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}