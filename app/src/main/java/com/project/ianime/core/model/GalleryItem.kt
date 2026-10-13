package com.project.ianime.core.model

import android.content.Context
import com.project.ianime.utils.Constants

/**
 * UI model passed to Compose
 */
data class GalleryItem(
    val animeId: String,
    val animeEnName: String,
    val animeChName: String,
    val watchStatus: WatchStatus,
    val year: String,
    val rate: String,
    val genreList: List<Genre>,
    val imageUrl: String?
){
    fun displaySubtitle(context: Context): String{
        val genres = genreList.joinToString(", ") { context.getString(it.label) }
        return listOf(year, genres)
            .filter { it.isNotBlank() }
            .joinToString(" · ")
    }

    fun displayName(context: Context): String =
        if (context.resources.configuration.locales[0].language == Constants.LANG_ZH) animeChName else animeEnName
}
