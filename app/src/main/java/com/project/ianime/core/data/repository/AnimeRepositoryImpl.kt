package com.project.ianime.core.data.repository

import androidx.annotation.WorkerThread
import com.project.ianime.core.data.local.AnimeDao
import com.project.ianime.core.data.local.AnimeEntity
import com.project.ianime.core.data.remote.api.AnimeApi
import com.project.ianime.core.data.remote.dto.AnimeApiModel
import com.project.ianime.core.data.remote.safeApiCall
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeApi: AnimeApi,
    private val animeDao: AnimeDao
) : AnimeRepository {

    // cached the data from the Network resource
    private var cachedAnimeItemsList : List<AnimeApiModel> ?= null

    override suspend fun getAnimeListFromNetwork(): List<AnimeApiModel> =
        safeApiCall {
            animeApi.getAnimeListFromNetwork().sortedByDescending {
                it.rate
            }
        }

    override fun getAnimeDetailsById(animeId: String) = animeDao.getAnimeById(animeId)

    @WorkerThread
    override suspend fun insertAnimeIntoDatabase(animeEntity: AnimeEntity) = animeDao.insertAnime(animeEntity)

    @WorkerThread
    override suspend fun clearOfflineAnimeList(){
        animeDao.deleteAll()
        animeDao.resetPrimaryKey()
    }

    override fun isDatabaseEmpty(): Boolean {
        return animeDao.getAnimeCount() == 0
    }

    @WorkerThread
    override suspend fun getOfflineAnimeListSynchronously(): List<AnimeEntity> {
        return animeDao.getAllAnimes().firstOrNull() ?: emptyList()
    }

}