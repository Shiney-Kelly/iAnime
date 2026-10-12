package com.project.ianime.core.data.remote.dto

import com.project.ianime.core.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AnimeGenreApiModel {
    @SerialName("LEGEND")
    LEGEND,

    @SerialName("HOT_BLOODED")
    HOT_BLOODED,

    @SerialName("ACTION")
    ACTION,

    @SerialName("ADVENTURE")
    ADVENTURE,

    @SerialName("COMEDY")
    COMEDY,

    @SerialName("DRAMA")
    DRAMA,

    @SerialName("FANTASY")
    FANTASY,

    @SerialName("MYSTERY")
    MYSTERY,

    @SerialName("ROMANCE")
    ROMANCE,

    @SerialName("SCI_FI")
    SCI_FI,

    @SerialName("SLICE_OF_LIFE")
    SLICE_OF_LIFE;
}

fun AnimeGenreApiModel.mapToGenre(): Genre = when (this) {
    AnimeGenreApiModel.LEGEND -> Genre.LEGEND
    AnimeGenreApiModel.HOT_BLOODED -> Genre.HOT_BLOODED
    AnimeGenreApiModel.ACTION -> Genre.ACTION
    AnimeGenreApiModel.ADVENTURE -> Genre.ADVENTURE
    AnimeGenreApiModel.COMEDY -> Genre.COMEDY
    AnimeGenreApiModel.DRAMA -> Genre.DRAMA
    AnimeGenreApiModel.FANTASY -> Genre.FANTASY
    AnimeGenreApiModel.MYSTERY -> Genre.MYSTERY
    AnimeGenreApiModel.ROMANCE -> Genre.ROMANCE
    AnimeGenreApiModel.SCI_FI -> Genre.SCI_FI
    AnimeGenreApiModel.SLICE_OF_LIFE -> Genre.SLICE_OF_LIFE
}
