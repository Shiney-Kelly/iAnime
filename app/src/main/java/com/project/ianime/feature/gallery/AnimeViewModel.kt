package com.project.ianime.feature.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.ianime.core.data.remote.error.BadRequestException
import com.project.ianime.core.data.remote.error.ConnectionException
import com.project.ianime.core.data.remote.error.ErrorType
import com.project.ianime.core.data.remote.error.NotFoundException
import com.project.ianime.core.data.remote.error.UnauthorizedException
import com.project.ianime.core.data.remote.dto.AnimeApiModel
import com.project.ianime.core.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeList = MutableLiveData<List<AnimeApiModel>>()
    val animeList: LiveData<List<AnimeApiModel>> = _animeList

    var animeUiState = MutableLiveData<AnimeUiState>()

    private val viewScopeSubscriptionTracker = CompositeDisposable()

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
     * get entire list of animes from the network
     */
    private fun loadAnimeListFromNetwork() {
        // set loading state
        animeUiState.postValue(AnimeUiState.Loading)

        repository.getAnimeListFromNetwork()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ animeItems ->
                Log.d("AnimeViewModel", "Fetched from network: ${animeItems.size} items")
                if (animeItems.isEmpty()) {
                    animeUiState.postValue(AnimeUiState.Empty)
                } else {
                    animeUiState.postValue(AnimeUiState.Success)
                    // clear existing data and set new data
                    _animeList.postValue(emptyList())
                    _animeList.postValue(animeItems)
                }
            }, {
                handleError(it)
            }
            ).also { viewScopeSubscriptionTracker.add(it) }
    }

    /**
     * get an anime with specific id
     * @param animeTargetId - anime target id
     */
    fun getAnimeById(animeTargetId: String, callback: (AnimeApiModel?) -> Unit) {
        callback(_animeList.value?.find { it.animeId == animeTargetId })
    }

    /**
     * handler network request error scenarios
     */
    private fun handleError(exception: Throwable) {
        when (exception) {
            is UnauthorizedException -> {
                animeUiState.postValue(AnimeUiState.Error(ErrorType.UNAUTHORIZED))
            }

            is NotFoundException -> {
                animeUiState.postValue((AnimeUiState.Error(ErrorType.NOT_FOUND)))
            }

            is ConnectionException -> {
                animeUiState.postValue(AnimeUiState.Error(ErrorType.CONNECTION))
            }

            is BadRequestException -> {
                animeUiState.postValue(AnimeUiState.Error(ErrorType.BAD_REQUEST))
            }

            else -> animeUiState.postValue(AnimeUiState.Error(ErrorType.GENERIC))
        }
    }

}