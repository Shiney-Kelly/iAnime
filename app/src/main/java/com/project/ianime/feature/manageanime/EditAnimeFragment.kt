package com.project.ianime.feature.manageanime

import com.project.ianime.R

class EditAnimeFragment : ManageAnimeFragment() {
    override fun updateActionBar(): Boolean {
        actionBarService.setTitle(getString(R.string.edit_anime_title), toolbar)
        actionBarService.setNavigateBackAction(toolbar, this)
        return true
    }

    override fun saveAnime() {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance() = EditAnimeFragment()
    }
}