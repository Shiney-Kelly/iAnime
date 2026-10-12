package com.project.ianime.core.data.remote.api

import com.project.ianime.core.data.remote.dto.AnimeApiModel
import retrofit2.http.GET

/**
 * API endpoints from the network
 */
interface AnimeApi {

    /**
     * Get anime list info. from the network
     */
    @GET("animes")
    suspend fun getAnimeListFromNetwork(): List<AnimeApiModel>
}