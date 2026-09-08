package com.project.ianime.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ianime.api.error.*
import com.project.ianime.api.model.AnimeApiModel
import com.project.ianime.di.qualifier.IoDispatcher
import com.project.ianime.repository.AnimeDataRepository
import com.project.ianime.screens.stateholder.AnimeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeDataRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _animeList = MutableLiveData<List<AnimeApiModel>>()
    val animeList: LiveData<List<AnimeApiModel>> = _animeList

    var animeUiState = MutableLiveData<AnimeUiState>()
    private var animeDetailData: AnimeApiModel? = null

    private val viewScopeSubscriptionTracker = CompositeDisposable()

    init {
        // initially load data from local storage when app restarts
        getAnimeList()
    }

    /**
     * load entire anime list with refresh control
     */
    fun getAnimeList(refresh: Boolean = false) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                // if refresh or database is empty
                if (refresh || repository.isDatabaseEmpty()) {
                    loadAnimeListFromNetwork()
                } else {
                    loadAnimeListFromLocalStorage()
                }
            }
        }
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
                    viewModelScope.launch {
                        // clear existing data from the database
                        withContext(ioDispatcher) {
                            repository.clearOfflineAnimeList()
                        }

                        // insert new data into the database
                        withContext(ioDispatcher) {
                            animeItems.forEach { anime ->
                                val eachAnimeEntity = anime.mapToAnimeEntity()
                                repository.insertAnimeIntoDatabase(eachAnimeEntity)
                            }
                        }
                    }

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
     * get anime details from the local storage
     */
    private fun loadAnimeListFromLocalStorage() {
        viewModelScope.launch {
            try {
                val animeEntityList = withContext(ioDispatcher) {
                    // This method should fetch the data without observing changes
                    repository.getOfflineAnimeListSynchronously()
                }

                if (animeEntityList.isEmpty()) {
                    animeUiState.postValue(AnimeUiState.Empty)
                } else {
                    val animeOfflineData = mutableListOf<AnimeApiModel>()
                    animeEntityList.forEach {
                        animeOfflineData.add(it.mapToAnimeItem())
                    }

                    Log.d("AnimeViewModel", "Loaded from local storage: ${animeOfflineData.size} items")
                    animeUiState.postValue(AnimeUiState.Success)
                    _animeList.postValue(animeOfflineData)
                }
            } catch (e: Exception) {
                animeUiState.postValue(AnimeUiState.Error(ErrorType.NOT_FOUND))
                Log.e("AnimeViewModel", "Error loading data from local storage", e)
            }
        }
    }

    /**
     * get an anime with specific id
     * @param animeTargetId - anime target id
     */
    fun getAnimeById(animeTargetId: String, callback: (AnimeApiModel?) -> Unit) {
        viewModelScope.launch {
            val animeEntity = repository.getAnimeDetailsById(animeTargetId).firstOrNull()
            animeDetailData = animeEntity?.mapToAnimeItem()
            callback(animeDetailData)
        }
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