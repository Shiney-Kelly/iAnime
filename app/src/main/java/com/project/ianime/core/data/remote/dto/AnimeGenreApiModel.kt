package com.project.ianime.core.data.remote.dto

import androidx.annotation.StringRes
import com.project.ianime.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AnimeGenreApiModel(@StringRes val label: Int) {
    @SerialName("ADVENTURE")
    ADVENTURE(R.string.genre_adventure),

    @SerialName("LOVE")
    LOVE(R.string.genre_love),

    @SerialName("SCHOOL")
    SCHOOL(R.string.genre_school),

    @SerialName("ENERGY")
    ENERGY(R.string.genre_energy),

    @SerialName("SCI-FI")
    SCI_FI(R.string.genre_sci),

    @SerialName("MYSTERY")
    MYSTERY(R.string.genre_mystery),

    @SerialName("LIFE")
    LIFE(R.string.genre_life),

    @SerialName("LEGEND")
    LEGEND(R.string.genre_legend);
}
