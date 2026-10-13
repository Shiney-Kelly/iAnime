package com.project.ianime.core.data.repository

import com.project.ianime.core.data.remote.dto.AnimeApiModel
import com.project.ianime.core.data.local.AnimeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing data from network and database
 */
interface AnimeRepository {

    /**
     * return entire list of all anime details from Rest API call
     */
    suspend fun getAnimeListFromNetwork(): List<AnimeApiModel>

    /**
     * return specific anime details by anime ID
     * @param animeId - id of the target anime
     */
    fun getAnimeDetailsById(animeId: String): Flow<AnimeEntity?>

    /**
     * add each anime into locale database
     */
    suspend fun insertAnimeIntoDatabase(animeEntity: AnimeEntity)

    /**
     * clear all data from the local database
     */
    suspend fun clearOfflineAnimeList()

    /**
     * check if the locale database is empty or not
     */
    fun isDatabaseEmpty(): Boolean

    /**
     * returns entire list of all anime details from the offline database synchronously only when called
     */
    suspend fun getOfflineAnimeListSynchronously(): List<AnimeEntity>
}