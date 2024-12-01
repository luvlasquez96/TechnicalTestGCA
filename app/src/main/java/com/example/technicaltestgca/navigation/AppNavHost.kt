package com.example.technicaltestgca.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.technicaltestgca.presentation.DesignScreen
import com.example.technicaltestgca.presentation.SelectionScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Selection.route
    ) {
        composable(route = Screen.Selection.route) {
            SelectionScreen(navController = navController) { polygon ->
                navController.navigate(Screen.Design.createRoute(polygon.name))
            }
        }
        composable(
            route = Screen.Design.route,
            arguments = listOf(navArgument("polygonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val polygonName = backStackEntry.arguments?.getString("polygonName") ?: ""
            DesignScreen(polygonName = polygonName)
        }
    }
}