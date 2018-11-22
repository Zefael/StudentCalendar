package com.blueamber.studentcalendar.ui

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blueamber.studentcalendar.Constants
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.base.BaseActivity
import com.blueamber.studentcalendar.ui.calendartasks.CalendarTasksFragment
import com.blueamber.studentcalendar.ui.common.WebviewDialog
import com.blueamber.studentcalendar.ui.settings.SettingsFragment
import com.emas.mondial.ui.main.Back
import com.emas.mondial.ui.main.LoadFragment
import com.emas.mondial.ui.main.NavigationState
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_calendar_tasks.*


class StudentCalendarActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int = R.layout.activity_calendar_tasks

    private lateinit var navigationDelegate: NavigationDelegate
    private lateinit var mainViewModel: MainViewModel

    override fun setupViews() {
        super.setupViews()
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navigationDelegate = NavigationDelegate(supportFragmentManager, contentFrame.id, false)
        navigationDelegate.navigate(CalendarTasksFragment())

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun setupViewModels() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun setupObservers() {
        mainViewModel.navigation.observe(this,
            Observer { navigationState -> navigationState?.let { onNavigationUpdate(it) } })
        mainViewModel.statusBarColor.observe(this,
            Observer { statusBarColor -> statusBarColor?.let { onStatusBarColorUpdate(it) } })
        mainViewModel.statusBarTitle.observe(this,
            Observer { title -> title?.let { onStatusBarTitleUpdate(it) } })
        mainViewModel.statusBarOptionIcon.observe(this,
            Observer { icon -> icon?.let { onStatusBarOptionIcon(it) } })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            navigationDelegate.handleBackPressed { isLastFragment ->
                if (isLastFragment) {
                    finish()
                } else {
                    super.onBackPressed()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.calendar_tasks, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            R.id.action_refresh_save -> {
                mainViewModel.refreshNeeded.value = true
                updateWidgetData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(it: MenuItem): Boolean {
        drawer_layout.closeDrawer(GravityCompat.START)
        when (it.itemId) {
            R.id.nav_planning -> {
                navigationDelegate.navigate(CalendarTasksFragment())
            }
            R.id.nav_week -> {
            }
            R.id.nav_web -> {
                supportFragmentManager?.let { WebviewDialog.show(it, Constants.URL_WEBSITE) }
            }
            R.id.nav_parameter -> {
                supportFragmentManager?.let { SettingsFragment.show(it) }
            }
            else -> return false
        }
        return true
    }

    private fun onNavigationUpdate(navigationState: NavigationState) {
        when (navigationState) {
            is Back -> onBackPressed()
            is LoadFragment -> navigationDelegate.navigate(navigationState.fragment)
        }
    }

    private fun onStatusBarColorUpdate(color: Int) {
        window.statusBarColor = color
    }

    private fun onStatusBarTitleUpdate(title: String) {
        toolbar.title = title
    }

    private fun onStatusBarOptionIcon(icon: Int) {
        toolbar.menu.getItem(0).icon = getDrawable(icon)
    }

    override fun onStop() {
        updateWidgetData()
        super.onStop()
    }

    private fun updateWidgetData() {
        val intent = Intent("com.student.calendar.action.APPWIDGET_UPDATE")
        sendBroadcast(intent)
    }
}
