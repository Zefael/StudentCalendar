package com.blueamber.studentcalendar.ui.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.NavigationFragment
import com.blueamber.studentcalendar.ui.base.BaseDialog
import com.blueamber.studentcalendar.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : BaseDialogFragment(), Injectable {

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val dialog = SettingsFragment()
            val args = Bundle()
            dialog.arguments = args

            dialog.show(fragmentManager, "SettingsFragment")
        }
    }

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

    override fun setupViews() {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialog)
        close_settings.setOnClickListener {
            dismiss()
        }
    }

    override fun setupData() {
        viewModel.downloadGroups()
        adapter = SettingsGroupsAdapter()
        val manager = LinearLayoutManager(requireContext())
        list_Group.layoutManager = manager
        list_Group.adapter = adapter
    }

    override fun onBackPressed(): Boolean {
        return false
    }
}