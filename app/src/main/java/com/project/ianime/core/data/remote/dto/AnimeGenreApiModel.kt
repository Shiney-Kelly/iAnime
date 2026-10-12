package com.project.ianime.core.data.remote.dto

import androidx.annotation.StringRes
import com.project.ianime.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AnimeGenreApiModel(@StringRes val label: Int) {
    @SerialName("LEGEND")
    LEGEND(R.string.genre_legend),

    @SerialName("HOT_BLOODED")
    HOT_BLOODED(R.string.genre_hot_blooded),

    @SerialName("ACTION")
    ACTION(R.string.genre_action),

    @SerialName("ADVENTURE")
    ADVENTURE(R.string.genre_adventure),

    @SerialName("COMEDY")
    COMEDY(R.string.genre_comedy),

    @SerialName("DRAMA")
    DRAMA(R.string.genre_drama),

    @SerialName("FANTASY")
    FANTASY(R.string.genre_fantasy),

    @SerialName("MYSTERY")
    MYSTERY(R.string.genre_mystery),

    @SerialName("ROMANCE")
    ROMANCE(R.string.genre_romance),

    @SerialName("SCI_FI")
    SCI_FI(R.string.genre_sci_fi),

    @SerialName("SLICE_OF_LIFE")
    SLICE_OF_LIFE(R.string.genre_slice_of_life);
}
