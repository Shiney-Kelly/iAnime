package com.project.ianime.di

import com.project.ianime.repository.AnimeDataRepository
import com.project.ianime.repository.AnimeDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsAnimeDataRepository(impl: AnimeDataRepositoryImpl): AnimeDataRepository
}
