package com.project.ianime.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {

    @Query("SELECT * FROM ianime_offline_table")
    fun getAllAnimes(): Flow<List<AnimeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(animeEntity: AnimeEntity)

    /**
     * suspend function ensures performing heavy-CPU tasks running in background
     */
    @Query("DELETE FROM ianime_offline_table")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) from ianime_offline_table")
    fun getAnimeCount(): Int

    @Query("SELECT * FROM ianime_offline_table WHERE anime_id = :animeId")
    fun getAnimeById(animeId: String): Flow<AnimeEntity?>

    @Query("DELETE FROM sqlite_sequence WHERE name = 'ianime_offline_table'")
    suspend fun resetPrimaryKey()
}