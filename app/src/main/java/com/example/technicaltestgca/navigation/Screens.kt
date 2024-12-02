package com.example.technicaltestgca.navigation

import com.example.technicaltestgca.domain.model.Polygon
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    object Selection : Screen("selection")
    object Design : Screen("design/{polygon}/{sidesCount}/{scale}") {
        fun createRoute(polygon: Polygon, sidesCount: Int, scale: Float): String {
            val polygonJson = Json.encodeToString(polygon)
            return "design/$polygonJson/$sidesCount/$scale"
        }
    }
}