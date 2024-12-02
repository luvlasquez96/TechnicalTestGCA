package com.example.technicaltestgca.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.technicaltestgca.domain.model.Polygon
import com.example.technicaltestgca.presentation.DesignScreen
import com.example.technicaltestgca.presentation.SelectionScreen
import kotlinx.serialization.json.Json

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Selection.route
    ) {
        composable(route = Screen.Selection.route) {
            SelectionScreen { polygon, sidesCount, scale ->
                navController.navigate(Screen.Design.createRoute(polygon, sidesCount, scale))
            }
        }
        composable(
            route = "design/{polygon}/{sidesCount}/{scale}",
            arguments = listOf(
                navArgument("polygon") { type = NavType.StringType },
                navArgument("sidesCount") { type = NavType.IntType },
                navArgument("scale") { type = NavType.FloatType }
            )
        ) { backStackEntry ->
            val polygonJson = backStackEntry.arguments?.getString("polygon") ?: ""
            val polygon = try {
                Json.decodeFromString<Polygon>(polygonJson)
            } catch (e: Exception) {
                Polygon(name = "Error", points = emptyList())
            }
            val sidesCount = backStackEntry.arguments?.getInt("sidesCount") ?: 3
            val scale = backStackEntry.arguments?.getFloat("scale") ?: 1.0f

            DesignScreen(
                polygon = polygon,
                sidesCount = sidesCount,
                scale = scale,
                navController = navController
            )
        }
    }
}