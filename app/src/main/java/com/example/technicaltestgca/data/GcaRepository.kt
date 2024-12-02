package com.example.technicaltestgca.data

import com.example.technicaltestgca.domain.model.Polygon

interface GcaRepository {
    suspend fun getPolygons(): List<Polygon>
    suspend fun savePolygon(polygon: Polygon)
    suspend fun getSavedPolygon(): Polygon?

    suspend fun deletePolygon(polygon: Polygon)
}