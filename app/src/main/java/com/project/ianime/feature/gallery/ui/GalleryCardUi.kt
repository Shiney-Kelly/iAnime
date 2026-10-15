package com.project.ianime.feature.gallery.ui

import android.content.Context
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import com.project.ianime.core.model.GalleryItem
import com.project.ianime.core.model.WatchStatus
import com.project.ianime.core.ui.theme.coverBrush

@Immutable
data class GalleryCardUi(
    val title: String,
    val subtitle: String,
    val letter: String,
    val watchStatus: WatchStatus,
    val rate: String,
    val coverBrush: Brush,
)

fun GalleryItem.toCardUi(context: Context): GalleryCardUi {
    val title = displayName(context)
    return GalleryCardUi(
        title = title,
        subtitle = displaySubtitle(context),
        letter = title.trim().firstOrNull()?.uppercase().orEmpty(),
        watchStatus = watchStatus,
        rate = rate,
        coverBrush = coverBrush(animeId),
    )
}
