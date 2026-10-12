package com.project.ianime.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.ianime.core.ui.root.BaseFragment

class AppNavigationImpl(private val fragmentManager: FragmentManager) : AppNavigation, AppNavigationLifecycle {

    private val fragmentStack = ArrayList<BaseFragment>()
    private val activeFragmentStack = ArrayList<BaseFragment>()
    private val fragmentLifeCycleListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            if (f is BaseFragment) {
                fragmentStack.add(f)
            }
        }

        override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
            if (f is BaseFragment) {
                fragmentStack.remove(f)
            }
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            if (f is BaseFragment) {
                activeFragmentStack.add(f)
            }
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            if (f is BaseFragment) {
                activeFragmentStack.remove(f)
            }
        }
    }

    /**
     * return the top active fragment from activeFragmentStack
     */
    private fun getTopActiveFragment(): BaseFragment? {
        val activeCount = activeFragmentStack.size
        if (activeCount == 0) {
            return null
        }
        return activeFragmentStack[activeCount - 1]
    }

    override fun showFragmentReplaceTop(fragment: Fragment, containerId: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun showFragmentOverTop(fragment: Fragment, containerId: Int) {
        val transaction = fragmentManager.beginTransaction()
        val topFragment = getTopActiveFragment()
        if (topFragment != null) {
            transaction.remove(topFragment)
        }
        transaction.add(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateBack(): Boolean {
        // Implemented for exit before dialog
        val topFragment = getTopActiveFragment() ?: return false
        if (topFragment.navigateBack()) {
            return true
        }

        if (fragmentManager.backStackEntryCount < 1) {
            return false
        }
        fragmentManager.popBackStack()
        return true
    }

    override fun closeTopFragment(): Boolean {
        if (fragmentManager.backStackEntryCount < 1) {
            return false
        }
        fragmentManager.popBackStack()
        return true
    }

    override fun onCreate() {
        fragmentManager.registerFragmentLifecycleCallbacks(fragmentLifeCycleListener, false)
    }

    override fun onDestroy() {
        fragmentManager.unregisterFragmentLifecycleCallbacks(fragmentLifeCycleListener)
    }

}