package com.project.ianime.core.ui.root

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.project.ianime.R
import com.project.ianime.navigation.AppNavigation
import com.project.ianime.navigation.AppNavigationImpl
import com.project.ianime.navigation.AppNavigationLifecycle
import com.project.ianime.core.ui.widget.actionbar.ActionBarService
import com.project.ianime.core.ui.widget.actionbar.ActionBarServiceImpl
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {
    protected var compositeDisposable = CompositeDisposable()

    lateinit var appNavigation: AppNavigation
    private lateinit var navigationLifeCycle: AppNavigationLifecycle
    val actionBarService: ActionBarService by lazy {
        ActionBarServiceImpl(
            activity as AppCompatActivity
        )
    }
    val baseContainerId = R.id.container

    override fun onAttach(context: Context) {
        val navigationManagerImpl = AppNavigationImpl(
            requireActivity().supportFragmentManager
        )
        appNavigation = navigationManagerImpl
        navigationLifeCycle = navigationManagerImpl
        super.onAttach(context)
    }

    // Set fragment action bar
    open fun updateActionBar(): Boolean {
        return false
    }

    // Set fragment navigate back behavior
    open fun navigateBack(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        navigationLifeCycle.onCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateActionBar()
    }

    override fun onDestroyView() {
        // clear listen to Observable when view is destroyed
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onDestroy() {
        navigationLifeCycle.onDestroy()
        super.onDestroy()
    }
}