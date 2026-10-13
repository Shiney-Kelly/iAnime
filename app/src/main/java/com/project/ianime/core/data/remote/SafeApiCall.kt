package com.project.ianime.core.data.remote

import com.project.ianime.core.data.remote.error.BadRequestException
import com.project.ianime.core.data.remote.error.ConnectionException
import com.project.ianime.core.data.remote.error.ContentOverSizeException
import com.project.ianime.core.data.remote.error.HttpStatus
import com.project.ianime.core.data.remote.error.NotFoundException
import com.project.ianime.core.data.remote.error.UnauthorizedException
import retrofit2.HttpException
import java.io.IOException

/**
 * Wrapper for api calls handle customized error messages
 */
suspend fun <T> safeApiCall(call: suspend () -> T): T =
    try {
        call()
    } catch (e: HttpException) {
        throw when (e.code()) {
            HttpStatus.BAD_REQUEST -> BadRequestException(e.message())
            HttpStatus.UNAUTHORIZED -> UnauthorizedException(e.message())
            HttpStatus.NOT_FOUND -> NotFoundException(e.message())
            HttpStatus.CONTENT_OVERSIZE -> ContentOverSizeException(e.message())
            in 500..599 -> ConnectionException(e.message())
            else -> e
        }
    } catch (e: IOException) {
        throw ConnectionException(e.message)
    }