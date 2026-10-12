package com.project.ianime.feature.gallery

import com.project.ianime.core.data.remote.error.ErrorType

/**
 * State holder for setting screen state for 'Success', 'Error', and 'Empty'
 */
sealed class AnimeUiState {

    object Success : AnimeUiState()

    object Empty : AnimeUiState()

    object Loading : AnimeUiState()

    data class Error(val errorType: ErrorType) : AnimeUiState()
}