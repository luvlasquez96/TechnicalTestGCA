package com.example.technicaltestgca.domain.usecase

import com.example.technicaltestgca.data.GcaRepository
import com.example.technicaltestgca.domain.model.Polygon
import javax.inject.Inject

class GetSavedPolygonUseCase @Inject constructor(
    private val repository: GcaRepository
) {
    suspend operator fun invoke(): Polygon? = repository.getSavedPolygon()
}