package com.example.technicaltestgca.data.remote

import com.example.technicaltestgca.data.dataAccess.GcaService
import com.example.technicaltestgca.data.remote.model.PolygonResponse
import javax.inject.Inject

class RemoteGcaDataSource @Inject constructor(
    private val polygonService: GcaService
) {
    suspend fun fetchPolygons(): List<PolygonResponse> {
        return polygonService.getPolygons()
    }
}