package com.project.ianime.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.ianime.R
import com.project.ianime.databinding.FragmentHomeBinding
import com.project.ianime.core.ui.root.BaseFragment
import com.project.ianime.feature.gallery.GalleryFragment
import com.project.ianime.feature.manageanime.AddAnimeFragment
import com.project.ianime.feature.user.UserFragment

class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var toolbar: Toolbar
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var addAnimeButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        toolbar = binding.topAppBar.toolBar
        bottomNavigationView = binding.bottomNavigationView
        addAnimeButton = binding.buttonAddAnime
        return binding.root
    }

    override fun updateActionBar(): Boolean {
        actionBarService.setTitle(getString(R.string.app_name), toolbar)
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // launch Main Screen
        navigateToAnimeGallery()

        // set bottom navigation
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_screen -> {
                    appNavigation.showFragmentOverTop(
                        GalleryFragment(),
                        fragmentContainerId
                    )
                    return@setOnItemSelectedListener true
                }

                R.id.user_screen -> {
                    appNavigation.showFragmentOverTop(
                        UserFragment(),
                        fragmentContainerId
                    )
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
        // navigate to add anime screen
        addAnimeButton.setOnClickListener {
            navigateToAddAnime()
        }
    }

    private fun navigateToAnimeGallery() {
        appNavigation.showFragmentReplaceTop(GalleryFragment(), fragmentContainerId)
    }

    private fun navigateToAddAnime() {
        appNavigation.showFragmentReplaceTop(AddAnimeFragment(), baseContainerId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}