package com.project.ianime.core.data.remote.error

/**
 * Handles specific exception such as 400, 401, 404, 413 and 5xx errors
 */
sealed class NetworkException(message: String?) : Exception(message) {
    class BadRequest(message: String?) : NetworkException(message)
    class Unauthorized(message: String?) : NetworkException(message)
    class NotFound(message: String?) : NetworkException(message)
    class ContentOversize(message: String?) : NetworkException(message)
    class Connection(message: String?) : NetworkException(message)
}
