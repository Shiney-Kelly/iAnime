package com.project.ianime.feature.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ianime.core.data.remote.dto.AnimeApiModel
import com.project.ianime.core.data.remote.error.toErrorType
import com.project.ianime.core.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeList = MutableLiveData<List<AnimeApiModel>>()
    val animeList: LiveData<List<AnimeApiModel>> = _animeList

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
                if (rawAnimeListData.isEmpty()){
                    _uiState.value = GalleryUiState.Empty
                } else {
                    _uiState.value = GalleryUiState.Loaded(
                        // TODO: should be mapped in repository instead
                        animeList = rawAnimeListData.map { it.mapToGalleryItem() }
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
    fun getAnimeById(animeTargetId: String, callback: (AnimeApiModel?) -> Unit) {
        callback(_animeList.value?.find { it.animeId == animeTargetId })
    }
}