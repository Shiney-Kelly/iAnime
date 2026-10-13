package com.project.ianime.core.ui.widget.actionbar

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.project.ianime.R
import com.project.ianime.core.ui.root.BaseFragment

class ActionBarServiceImpl(private val activity: AppCompatActivity) : ActionBarService {
    override fun setTitle(title: String, toolbar: Toolbar) {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar ?: return
        actionBar.setDisplayShowTitleEnabled(true)
        actionBar.title = title
    }

    override fun setNavigateBackAction(toolbar: Toolbar, activeFragment: BaseFragment) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        // call navigate back method which includes check for navigate back conditions
        toolbar.setNavigationOnClickListener {
            activeFragment.appNavigation.navigateBack()
        }
    }
}