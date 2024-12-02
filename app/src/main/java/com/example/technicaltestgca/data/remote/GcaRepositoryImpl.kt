package com.example.technicaltestgca.data.remote

import com.example.technicaltestgca.data.GcaRepository
import com.example.technicaltestgca.data.local.LocalGcaDataSource
import com.example.technicaltestgca.domain.model.Polygon
import javax.inject.Inject

class GcaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteGcaDataSource,
    private val localDataSource: LocalGcaDataSource
) : GcaRepository {
    override suspend fun getPolygons(): List<Polygon> {
        val cachedPolygons = localDataSource.getCachedPolygons()
        return if (cachedPolygons.isNotEmpty()) {
            cachedPolygons.map { it.toDomainModel() }
        } else {
            val remotePolygons = remoteDataSource.fetchPolygons()
            localDataSource.cachePolygons(remotePolygons)
            remotePolygons.map { it.toDomainModel() }
        }
    }

    override suspend fun savePolygon(polygon: Polygon) {
        val polygonDataModel = polygon.toDataModel()
        localDataSource.savePolygon(polygonDataModel)
    }

    override suspend fun getSavedPolygon(): Polygon? {
        return localDataSource.getSavedPolygon()?.toDomainModel()
    }
}