package com.project.ianime.core.data.remote.dto

import com.project.ianime.core.data.local.AnimeEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * API response for get single anime details
 */
@Serializable
data class AnimeApiModel(
    @SerialName("anime_id") val animeId: String,
    @SerialName("anime_en_name") val animeEnName: String,
    @SerialName("anime_ch_name") val animeChName: String,
    @SerialName("rate") val rate: Float,
    @SerialName("watching_status") val watchingStatus: AnimeWatchingStatusApiModel,
    // could belong to 1 or more genre
    @SerialName("genre") val genres: List<AnimeGenreApiModel>,
    @SerialName("release_year") val releaseYear: String? = null,
    @SerialName("synopsis") val synopsis: String? = null,
    @SerialName("cover_url") val animeImageUrl: String? = null
) {

    // conversion to database entity object
    fun mapToAnimeEntity(): AnimeEntity {
        return AnimeEntity(
            animeId = animeId,
            animeName = animeName,
            rate = rate,
            status = status,
            country = country,
            type = type,
            releaseYear = releaseYear,
            synopsis = synopsis,
            animeImageUrl = animeImageUrl
        )
    }
}