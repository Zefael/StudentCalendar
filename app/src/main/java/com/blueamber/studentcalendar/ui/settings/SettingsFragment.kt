package com.blueamber.studentcalendar.ui.settings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.modules.Injectable
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

    override fun getLayoutId(): Int = R.layout.settings_fragment

    override fun setupViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    override fun setupObservers() {
        viewModel.groups.observe(this,
            Observer<List<Groups>> { it -> it?.let { updateGroups(it) } })
    }

    override fun preInit() {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialog)
    }

    override fun setupViews() {
        dialog_close.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setupData() {
        viewModel.downloadGroups()
//        adapter = SettingsGroupsAdapter(viewModel)
//        val manager = LinearLayoutManager(requireContext())
//        list_Group.layoutManager = manager
//        list_Group.adapter = adapter
    }

    private fun updateGroups(groups: List<Groups>) {
        groups.forEach {
            val itemGroups = layoutInflater.inflate(R.layout.settings_group_item, null)
            val switcher = itemGroups.findViewById<Switch>(R.id.group_switch)
            val originalGroup = itemGroups.findViewById<TextView>(R.id.group_original_name)
            val newGroup = itemGroups.findViewById<EditText>(R.id.group_new_name)

            switcher.isChecked = it.visibility
            originalGroup.text = it.originalGroups
            newGroup.setText(it.newGroups)

            switcher.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateVisibility(isChecked, it.originalGroups)
            }

            newGroup.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty()) {
                        viewModel.updateNewGroup(s.toString(), it.originalGroups)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            list_Group.addView(itemGroups)
        }
    }
}