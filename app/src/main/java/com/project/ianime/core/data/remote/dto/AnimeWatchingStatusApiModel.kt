package com.project.ianime.core.data.remote.dto

import com.project.ianime.core.model.WatchStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AnimeWatchingStatusApiModel {
    @SerialName("COMPLETED")
    COMPLETED,

    @SerialName("IN_PROGRESS")
    IN_PROGRESS,

    @SerialName("NOT_STARTED")
    NOT_STARTED;
}

fun AnimeWatchingStatusApiModel.mapToWatchStatus(): WatchStatus = when (this) {
    AnimeWatchingStatusApiModel.NOT_STARTED -> WatchStatus.PLAN
    AnimeWatchingStatusApiModel.IN_PROGRESS -> WatchStatus.WATCHING
    AnimeWatchingStatusApiModel.COMPLETED -> WatchStatus.DONE
}
