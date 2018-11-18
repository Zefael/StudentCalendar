package com.blueamber.studentcalendar.ui.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.base.BaseFragment
import kotlinx.android.synthetic.main.settings_fragment.*
import javax.inject.Inject


class SettingsFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SettingsViewModel
    private lateinit var adapter: SettingsGroupsAdapter

    override fun getLayoutId(): Int = R.layout.settings_fragment

    override fun setupViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    override fun setupObservers() {
        viewModel.groups.observe(this,
            Observer<List<Groups>> { it -> it?.let { adapter.update(it, true) } })
    }

    override fun setupData() {
        viewModel.downloadGroups()
        adapter = SettingsGroupsAdapter()
        val manager = LinearLayoutManager(requireContext())
        list_Group.layoutManager = manager
        list_Group.adapter = adapter
    }
}