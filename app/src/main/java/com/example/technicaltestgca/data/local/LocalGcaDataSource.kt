package com.example.technicaltestgca.data.local

import android.content.Context
import com.example.technicaltestgca.data.remote.model.PolygonResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalGcaDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val sharedPreferences =
        context.getSharedPreferences("PolygonCache", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getCachedPolygons(): List<PolygonResponse> {
        val polygonsJson = sharedPreferences.getString("polygons", null)
        return polygonsJson?.let {
            val type = object : TypeToken<List<PolygonResponse>>() {}.type
            gson.fromJson(it, type)
        } ?: emptyList()
    }

    fun cachePolygons(polygons: List<PolygonResponse>) {
        val polygonsJson = gson.toJson(polygons)
        sharedPreferences.edit()
            .putString("polygons", polygonsJson)
            .apply()
    }

    fun savePolygon(polygon: PolygonResponse) {
        val cachedPolygons = getCachedPolygons().toMutableList()
        cachedPolygons.add(polygon)
        cachePolygons(cachedPolygons)
        sharedPreferences.edit()
            .putString("saved_polygon", gson.toJson(polygon))
            .apply()
    }

    fun getSavedPolygon(): PolygonResponse? {
        val savedPolygonJson = sharedPreferences.getString("saved_polygon", null)
        return savedPolygonJson?.let {
            gson.fromJson(it, PolygonResponse::class.java)
        }
    }

    fun deletePolygon(polygon: PolygonResponse) {
        val cachedPolygons = getCachedPolygons().toMutableList()
        cachedPolygons.removeIf { it.points == polygon.points }
        cachePolygons(cachedPolygons)
    }
}