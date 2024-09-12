package com.rizalanggoro.presence.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rizalanggoro.presence.ui.screens.classroom.ClassroomRoute
import com.rizalanggoro.presence.ui.screens.classroom.ClassroomScreen
import com.rizalanggoro.presence.ui.screens.classroom.detail.DetailClassroomRoute
import com.rizalanggoro.presence.ui.screens.classroom.detail.DetailClassroomScreen
import com.rizalanggoro.presence.ui.screens.config.ConfigRoute
import com.rizalanggoro.presence.ui.screens.config.ConfigScreen
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
                onNavigateToSetting = { navController.navigate(SettingRoute) },
                onNavigateToDetailClassroom = {
                    navController.navigate(DetailClassroomRoute(it))
                }
            )
        }

        // setting
        composable<SettingRoute> {
            SettingScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToClassroom = { navController.navigate(ClassroomRoute) },
                onNavigateToImportConfig = { navController.navigate(ConfigRoute) },
            )
        }

        // classroom
        composable<ClassroomRoute> {
            ClassroomScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
        composable<DetailClassroomRoute> {
            val arguments = it.toRoute<DetailClassroomRoute>()
            DetailClassroomScreen(
                classroomId = arguments.classroomId,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        // import
        composable<ConfigRoute> {
            ConfigScreen()
        }
    }
}