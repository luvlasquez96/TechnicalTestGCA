package com.example.technicaltestgca.di

import android.content.Context
import com.example.technicaltestgca.data.GcaRepository
import com.example.technicaltestgca.data.dataAccess.GcaService
import com.example.technicaltestgca.data.local.LocalGcaDataSource
import com.example.technicaltestgca.data.remote.GcaRepositoryImpl
import com.example.technicaltestgca.data.remote.RemoteGcaDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideGcaRepository(
        remoteDataSource: RemoteGcaDataSource,
        localDataSource: LocalGcaDataSource
    ): GcaRepository {
        return GcaRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
}