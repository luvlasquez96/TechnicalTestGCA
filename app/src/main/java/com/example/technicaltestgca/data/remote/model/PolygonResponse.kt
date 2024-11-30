package com.example.technicaltestgca.data.remote.model

data class PolygonResponse(
    val name: String,
    val points: List<PointResponse>
)

data class PointResponse(
    val x: Float,
    val y: Float
)
