package com.example.technicaltestgca.navigation

sealed class Screen(val route: String) {
    object Selection : Screen("selection")
    object Design : Screen("design/{polygonName}") {
        fun createRoute(polygonName: String) = "design/$polygonName"
    }
}