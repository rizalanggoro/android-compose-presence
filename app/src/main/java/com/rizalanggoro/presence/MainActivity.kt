package com.rizalanggoro.presence

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rizalanggoro.presence.ui.screens.ClassroomRoute
import com.rizalanggoro.presence.ui.screens.CreateClassroomRoute
import com.rizalanggoro.presence.ui.screens.DetailClassroomRoute
import com.rizalanggoro.presence.ui.screens.HomeRoute
import com.rizalanggoro.presence.ui.screens.SettingRoute
import com.rizalanggoro.presence.ui.screens.classroom.ClassroomScreen
import com.rizalanggoro.presence.ui.screens.classroom.create.CreateClassroomScreen
import com.rizalanggoro.presence.ui.screens.home.HomeScreen
import com.rizalanggoro.presence.ui.screens.setting.SettingScreen
import com.rizalanggoro.presence.ui.theme.PresenceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PresenceTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHost(
                        navController,
                        startDestination = HomeRoute,
                    ) {
                        composable<HomeRoute> { HomeScreen(navController) }
                        composable<SettingRoute> { SettingScreen(navController) }

                        // classroom
                        composable<CreateClassroomRoute> {
                            CreateClassroomScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable<ClassroomRoute> {
                            ClassroomScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToCreate = { navController.navigate(CreateClassroomRoute) },
                                onNavigateToDetail = {
                                    navController.navigate(
                                        DetailClassroomRoute(
                                            id = it
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}
