package com.project.ianime.core.data.repository

import androidx.annotation.WorkerThread
import com.project.ianime.core.data.remote.error.ConnectionException
import com.project.ianime.core.data.local.AnimeDao
import com.project.ianime.core.data.local.AnimeEntity
import com.project.ianime.core.data.remote.api.AnimeApi
import com.project.ianime.core.data.remote.dto.AnimeApiModel
import com.project.ianime.core.data.remote.error.BadRequestException
import com.project.ianime.core.data.remote.error.ContentOverSizeException
import com.project.ianime.core.data.remote.error.HttpStatus
import com.project.ianime.core.data.remote.error.NotFoundException
import com.project.ianime.core.data.remote.error.UnauthorizedException
import kotlinx.coroutines.flow.firstOrNull
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeApi: AnimeApi,
    private val animeDao: AnimeDao
) : AnimeRepository {

    // cached the data from the Network resource
    private var cachedAnimeItemsList : List<AnimeApiModel> ?= null

    override suspend fun getAnimeListFromNetwork(): List<AnimeApiModel> =
        try {
            animeApi.getAnimeListFromNetwork().sortedByDescending {
                it.rate
            }
        } catch (e: HttpException){
            throw when(e.code()){
                HttpStatus.BAD_REQUEST -> BadRequestException(e.message())
                HttpStatus.UNAUTHORIZED -> UnauthorizedException(e.message())
                HttpStatus.NOT_FOUND -> NotFoundException(e.message())
                HttpStatus.CONTENT_OVERSIZE -> ContentOverSizeException(e.message())
                in 500..599 -> ConnectionException(e.message())
                else -> e
            }

        } catch (e: IOException){
            throw ConnectionException(e.message)
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