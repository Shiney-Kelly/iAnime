package com.project.ianime.core.data.remote

import com.project.ianime.core.data.remote.error.HttpStatus
import com.project.ianime.core.data.remote.error.NetworkException
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
            HttpStatus.BAD_REQUEST -> NetworkException.BadRequest(e.message())
            HttpStatus.UNAUTHORIZED -> NetworkException.Unauthorized(e.message())
            HttpStatus.NOT_FOUND -> NetworkException.NotFound(e.message())
            HttpStatus.CONTENT_OVERSIZE -> NetworkException.ContentOversize(e.message())
            in 500..599 -> NetworkException.Connection(e.message())
            else -> e
        }
    } catch (e: IOException) {
        throw NetworkException.Connection(e.message)
    }
