package com.project.ianime.core.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.project.ianime.R
import com.project.ianime.core.ui.theme.StatusDone
import com.project.ianime.core.ui.theme.StatusPlan
import com.project.ianime.core.ui.theme.StatusWatching

enum class WatchStatus(@StringRes val label: Int) {
    DONE(R.string.status_completed),
    PLAN(R.string.status_not_started),
    WATCHING(R.string.status_in_progress);
}

fun WatchStatus.mapToStatusColor(): Color = when(this){
    WatchStatus.DONE -> StatusDone
    WatchStatus.WATCHING -> StatusWatching
    WatchStatus.PLAN -> StatusPlan
}
