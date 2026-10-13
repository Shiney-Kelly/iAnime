package com.project.ianime.core.data.remote.error

/**
 * Handles specific exception such as 400, 401, 404, 413 and 5xx errors
 */
class UnauthorizedException(message: String?) : Throwable(message)

class NotFoundException(message: String?) : Throwable(message)

class ConnectionException(message: String?) : Throwable(message)

class ContentOverSizeException(message: String?) : Throwable(message)

class BadRequestException(message: String?) : Throwable(message)
