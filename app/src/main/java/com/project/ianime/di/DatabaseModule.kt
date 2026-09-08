package com.project.ianime.di

import android.content.Context
import androidx.room.Room
import com.project.ianime.data.AnimeDao
import com.project.ianime.data.AnimeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "ianime_database"

    @Provides
    @Singleton
    fun providesAnimeDatabase(@ApplicationContext context: Context): AnimeDatabase =
        Room.databaseBuilder(context, AnimeDatabase::class.java, DATABASE_NAME).build()

    @Provides
    fun providesAnimeDao(database: AnimeDatabase): AnimeDao = database.animeDao()
}
