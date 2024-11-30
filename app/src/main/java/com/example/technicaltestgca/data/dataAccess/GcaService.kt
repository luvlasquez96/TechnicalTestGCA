package com.example.technicaltestgca.data.dataAccess

import com.example.technicaltestgca.data.remote.model.PolygonResponse
import retrofit2.http.GET

interface GcaService {
    @GET("pruebamovil/api/polygons")
    suspend fun getPolygons(): List<PolygonResponse>
}