package com.blueamber.studentcalendar.ui.settings

import android.view.ViewGroup
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.ui.base.recycler.BaseHolder
import com.blueamber.studentcalendar.ui.base.recycler.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.settings_group_item.*

class SettingsGroupsAdapter: BaseRecyclerAdapter<Groups>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<Groups> =
            GroupsHolder(parent)

    class GroupsHolder(parent: ViewGroup): BaseHolder<Groups>(parent, R.layout.settings_group_item) {

        override fun display() {
            data?.let {
                group_switch.isChecked = it.visibility
                group_original_name.text = it.originalGroups
                group_new_name.setText(it.newGroups)
            }
        }
    }
}