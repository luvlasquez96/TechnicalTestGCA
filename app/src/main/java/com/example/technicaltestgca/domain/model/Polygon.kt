package com.example.technicaltestgca.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class Polygon(
    val name: String,
    val points: List<Point>
)

@Serializable
data class Point(
    val x: Float,
    val y: Float
)