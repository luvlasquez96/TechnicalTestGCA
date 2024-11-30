package com.example.technicaltestgca.domain.model

data class Polygon(
    val name: String,
    val points: List<Point>
)

data class Point(
    val x: Float,
    val y: Float
)