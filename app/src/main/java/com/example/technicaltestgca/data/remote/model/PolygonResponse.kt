package com.example.technicaltestgca.data.remote.model

data class PolygonResponse(
    val name: String,
    val points: List<Point>
)

data class Point(
    val x: Float,
    val y: Float
)
