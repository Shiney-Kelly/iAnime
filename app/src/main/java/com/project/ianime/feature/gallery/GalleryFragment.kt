package com.project.ianime.feature.gallery

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.activityViewModels
import com.project.ianime.core.ui.root.BaseFragment
import com.project.ianime.feature.gallery.ui.GalleryScreen
import com.project.ianime.feature.manageanime.AddAnimeFragment
import com.project.ianime.feature.viewanime.AnimeDetailFragment
import com.project.ianime.utils.Constants
import com.project.ianime.utils.updateLanguageSetting
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : BaseFragment() {

    private val galleryViewModel: GalleryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            MaterialTheme {
                GalleryScreen(
                    viewModel = galleryViewModel,
                    onAnimeClick = ::openAnimeDetail,
                    onAddAnimeClick = ::openAddAnime,
                    onLanguageClick = ::toggleLanguage,
                    onThemeClick = ::toggleTheme
                )
            }
        }
    }

    private fun openAnimeDetail(animeId: String) {
        val animeDetailFragment = AnimeDetailFragment().apply {
            arguments = Bundle().apply { putString(ANIME_ID_PARAM, animeId) }
        }
        appNavigation.showFragmentReplaceTop(animeDetailFragment, baseContainerId)
    }

    private fun openAddAnime() {
        appNavigation.showFragmentReplaceTop(AddAnimeFragment(), baseContainerId)
    }

    private fun toggleLanguage() {
        val isChinese = AppCompatDelegate.getApplicationLocales()
            .toLanguageTags()
            .startsWith(Constants.LANG_ZH)
        updateLanguageSetting(
            requireContext(),
            if (isChinese) Constants.LANG_EN else Constants.LANG_ZH
        )
    }

    private fun toggleTheme() {
        val isDark = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) ==
            Configuration.UI_MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
        )
    }

    companion object {
        const val ANIME_ID_PARAM = "anime_id"
    }
}
