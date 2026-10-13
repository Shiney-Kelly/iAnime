package com.project.ianime.feature.gallery

import com.project.ianime.core.data.remote.error.ErrorType
import com.project.ianime.core.model.GalleryItem

/**
 * State holder for setting screen state for 'Success', 'Error', and 'Empty'
 */
sealed interface GalleryUiState{
    data class Loaded(val animeList: List<GalleryItem>): GalleryUiState
    data object Loading: GalleryUiState
    data class Error(val errorType: ErrorType): GalleryUiState
    data object Empty: GalleryUiState
}