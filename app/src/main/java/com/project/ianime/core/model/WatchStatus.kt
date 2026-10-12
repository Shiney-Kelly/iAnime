package com.project.ianime.core.model

import androidx.annotation.StringRes
import com.project.ianime.R

enum class WatchStatus(@StringRes val label: Int) {
    DONE(R.string.status_completed),
    PLAN(R.string.status_not_started),
    WATCHING(R.string.status_in_progress);
}
