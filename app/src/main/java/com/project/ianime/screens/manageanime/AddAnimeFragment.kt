package com.project.ianime.screens.manageanime

import androidx.fragment.app.viewModels
import com.project.ianime.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddAnimeFragment : ManageAnimeFragment() {

    private val viewModel: AddAnimeViewModel by viewModels()

    override fun updateActionBar(): Boolean {
        actionBarService.setTitle(getString(R.string.add_anime_title), toolbar)
        actionBarService.setNavigateBackAction(toolbar, this)
        return true
    }

    override fun saveAnime() {
        TODO("Not yet implemented")
    }

}