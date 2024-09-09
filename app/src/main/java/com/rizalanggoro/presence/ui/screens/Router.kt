package com.rizalanggoro.presence.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rizalanggoro.presence.ui.screens.classroom.ClassroomRoute
import com.rizalanggoro.presence.ui.screens.classroom.ClassroomScreen
import com.rizalanggoro.presence.ui.screens.home.HomeRoute
import com.rizalanggoro.presence.ui.screens.home.HomeScreen
import com.rizalanggoro.presence.ui.screens.setting.SettingRoute
import com.rizalanggoro.presence.ui.screens.setting.SettingScreen

@Composable
fun Router() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = HomeRoute) {
        // home
        composable<HomeRoute> {
            HomeScreen(
                onNavigateToSetting = { navController.navigate(SettingRoute) }
            )
        }

        // setting
        composable<SettingRoute> {
            SettingScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToClassroom = { navController.navigate(ClassroomRoute) }
            )
        }

        // classroom
        composable<ClassroomRoute> {
            ClassroomScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}