package com.project.ianime.core.data.repository

import com.project.ianime.core.data.remote.api.AnimeApi
import com.project.ianime.core.data.remote.dto.AnimeApiModel
import com.project.ianime.core.data.remote.safeApiCall
import com.project.ianime.core.model.GalleryItem
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeApi: AnimeApi
) : AnimeRepository {

    // cached the data from the Network resource
    private var cachedAnimeItemsList: List<AnimeApiModel>? = null

    override suspend fun getAnimeListFromNetwork(): List<GalleryItem> =
        safeApiCall {
            animeApi.getAnimeListFromNetwork().map { it.mapToGalleryItem() }
                .sortedByDescending { it.rate }
        }
}
