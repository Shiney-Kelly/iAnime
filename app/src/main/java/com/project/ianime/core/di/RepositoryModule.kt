package com.project.ianime.core.di

import com.project.ianime.core.data.repository.AnimeRepository
import com.project.ianime.core.data.repository.AnimeRepositoryImpl
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
    abstract fun bindsAnimeDataRepository(impl: AnimeRepositoryImpl): AnimeRepository
}
