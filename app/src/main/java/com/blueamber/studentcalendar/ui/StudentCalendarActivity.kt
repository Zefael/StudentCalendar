package com.blueamber.studentcalendar.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.base.BaseActivity
import com.blueamber.studentcalendar.ui.calendartasks.CalendarTasksFragment
import com.emas.mondial.ui.main.Back
import com.emas.mondial.ui.main.LoadFragment
import com.emas.mondial.ui.main.NavigationState
import kotlinx.android.synthetic.main.activity_calendar_tasks.*
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*

class StudentCalendarActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun getLayoutId(): Int = R.layout.activity_calendar_tasks

    private lateinit var navigationDelegate: NavigationDelegate
    private lateinit var mainViewModel : MainViewModel

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
            Observer { statusBarColor -> statusBarColor?.let { onStatusBarColorUpdate(it) }})
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
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_camera -> {

            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onNavigationUpdate(navigationState: NavigationState) {
        when(navigationState) {
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
}
