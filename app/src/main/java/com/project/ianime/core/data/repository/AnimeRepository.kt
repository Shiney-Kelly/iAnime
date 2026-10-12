package com.project.ianime.core.data.repository

import com.project.ianime.core.data.remote.dto.AnimeApiModel

/**
 * Repository for managing data from network
 */
interface AnimeRepository {

    /**
     * return entire list of all anime details from Rest API call
     */
    suspend fun getAnimeListFromNetwork(): List<AnimeApiModel>
}
