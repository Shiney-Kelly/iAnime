package com.project.ianime.core.model

import androidx.annotation.StringRes
import com.project.ianime.R

enum class Genre(@StringRes val label: Int) {
    LEGEND(R.string.genre_legend),
    HOT_BLOODED(R.string.genre_hot_blooded),
    ACTION(R.string.genre_action),
    ADVENTURE(R.string.genre_adventure),
    COMEDY(R.string.genre_comedy),
    DRAMA(R.string.genre_drama),
    FANTASY(R.string.genre_fantasy),
    MYSTERY(R.string.genre_mystery),
    ROMANCE(R.string.genre_romance),
    SCI_FI(R.string.genre_sci_fi),
    SLICE_OF_LIFE(R.string.genre_slice_of_life);
}
