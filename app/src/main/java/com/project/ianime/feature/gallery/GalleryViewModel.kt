package com.project.ianime.feature.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ianime.core.data.remote.error.toErrorType
import com.project.ianime.core.data.repository.AnimeRepository
import com.project.ianime.core.model.GalleryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private var rawAnimeList: List<GalleryItem> = emptyList()

    private val _uiState = MutableStateFlow<GalleryUiState>(GalleryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAnimeList()
    }

    /**
     * load entire anime list
     */
    fun getAnimeList() {
        loadAnimeListFromNetwork()
    }

    /**
     * get entire list of anime from the network
     */
    private fun loadAnimeListFromNetwork() {
        // set loading state
        _uiState.value = GalleryUiState.Loading

        viewModelScope.launch {
            try {
                val rawAnimeListData = repository.getAnimeListFromNetwork()
                rawAnimeList = rawAnimeListData
                if (rawAnimeListData.isEmpty()){
                    _uiState.value = GalleryUiState.Empty
                } else {
                    _uiState.value = GalleryUiState.Loaded(
                        animeList = rawAnimeListData.toImmutableList()
                    )
                }
            } catch (e: Exception){
                if (e is CancellationException) throw e
                _uiState.value = GalleryUiState.Error(e.toErrorType())
            }
        }
    }

    /**
     * get an anime with specific id
     * @param animeTargetId - anime target id
     */
    fun getAnimeById(animeTargetId: String, callback: (GalleryItem?) -> Unit) {
        callback(rawAnimeList.find { it.animeId == animeTargetId })
    }
}