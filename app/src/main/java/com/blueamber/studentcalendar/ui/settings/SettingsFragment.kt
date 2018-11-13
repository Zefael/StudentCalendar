package com.blueamber.studentcalendar.ui.settings

import android.arch.lifecycle.ViewModelProviders
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.NavigationFragment

/**
 * Need :
 * Sélectionner les groupes à afficher (liste avec plusieurs items sélectionnables/color)
 * Renommer un group
 */
class SettingsFragment : NavigationFragment() {

    private lateinit var viewModel: SettingsViewModel

    override fun getLayoutId(): Int = R.layout.settings_fragment

    override fun setupViewModels() {
        super.setupViewModels()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    override fun setupViews() {
    }

    override fun setupObservers() {
    }

    override fun setupData() {
    }
}