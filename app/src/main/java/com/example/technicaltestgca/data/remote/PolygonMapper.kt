package com.example.technicaltestgca.data.remote

import com.example.technicaltestgca.data.remote.model.PointResponse
import com.example.technicaltestgca.data.remote.model.PolygonResponse
import com.example.technicaltestgca.domain.model.Point
import com.example.technicaltestgca.domain.model.Polygon

fun PolygonResponse.toDomainModel(): Polygon {
    return Polygon(
        name = this.name,
        points = this.points.map { it.toDomainModel() }
    )
}

fun PointResponse.toDomainModel(): Point {
    return Point(x = this.x, y = this.y)
}

fun Polygon.toDataModel(): PolygonResponse {
    return PolygonResponse(
        name = this.name,
        points = this.points.map { it.toDataModel() }
    )
}

fun Point.toDataModel(): PointResponse {
    return PointResponse(x = this.x, y = this.y)
}