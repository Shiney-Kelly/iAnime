package com.project.ianime.core.data.remote.dto

import androidx.annotation.StringRes
import com.project.ianime.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AnimeWatchingStatusApiModel(@StringRes val label: Int) {
    @SerialName("COMPLETED")
    COMPLETED(R.string.status_completed),

    @SerialName("IN_PROGRESS")
    IN_PROGRESS(R.string.status_in_progress),

    @SerialName("NOT_STARTED")
    NOT_STARTED(R.string.status_not_started);
}
