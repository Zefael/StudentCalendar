package com.blueamber.studentcalendar.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.blueamber.studentcalendar.Constants
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.base.BaseActivity
import com.blueamber.studentcalendar.ui.calendartasks.CalendarTasksFragment
import com.blueamber.studentcalendar.ui.common.WebviewDialog
import com.emas.mondial.ui.main.Back
import com.emas.mondial.ui.main.LoadFragment
import com.emas.mondial.ui.main.NavigationState
import com.enterprise.baseproject.extensions.toast
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

        navigationDelegate = NavigationDelegate(supportFragmentManager, contentFrame.id)
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
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.calendar_tasks, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                "Test Update".toast(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(it: MenuItem): Boolean {
        return when (it.itemId) {
            R.id.nav_planning -> {
                true
            }
            R.id.nav_week -> {
                true
            }
            R.id.nav_web -> {
                supportFragmentManager?.let { WebviewDialog.show(it, Constants.URL_WEBSITE) }
                true
            }
            R.id.nav_parameter -> {
                true
            }
            else -> false
        }
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

    override fun onStop() {
        val intent = Intent("com.student.calendar.action.APPWIDGET_UPDATE")
        sendBroadcast(intent)
        super.onStop()
    }
}
