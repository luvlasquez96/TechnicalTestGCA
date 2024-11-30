package com.example.technicaltestgca.di

import com.example.technicaltestgca.data.dataAccess.GcaService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GcaModule {
    @Provides
    @Singleton
    fun providePolygonService(retrofit: Retrofit): GcaService {
        return retrofit.create(GcaService::class.java)
    }
}