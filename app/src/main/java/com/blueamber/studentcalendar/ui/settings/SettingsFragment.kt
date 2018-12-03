package com.blueamber.studentcalendar.ui.settings

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.base.BaseDialogFragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
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
        val window = dialog?.window
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        dialog_close.setOnClickListener {
            dismiss()
        }
        enable_all_group.setOnClickListener {
            viewModel.changeAllVisibility(true)
        }
        disable_all_group.setOnClickListener {
            viewModel.changeAllVisibility(false)
        }
        alarm_actived.setOnCheckedChangeListener { _, isChecked ->
            alarm_parameter.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        reset_group.setOnClickListener { showAlertReinitGroups() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    override fun setupData() {
        viewModel.downloadGroups()
    }

    private fun updateGroups(groups: List<Groups>) {
        list_Group.removeAllViews()
        groups.forEach {
            val itemGroups = layoutInflater.inflate(R.layout.settings_group_item, null)
            val switcher = itemGroups.findViewById<SwitchMaterial>(R.id.group_switch)
            val originalGroup = itemGroups.findViewById<TextView>(R.id.group_original_name)
            val newGroup = itemGroups.findViewById<TextInputEditText>(R.id.group_new_name)

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

    private fun showAlertReinitGroups() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.dialog_reset_group_title)
                setMessage(R.string.dialog_reset_group_message)
                setPositiveButton(R.string.reset) { _, _ ->
                    viewModel.reinitAllGroups()
                }
                setNegativeButton(R.string.cancel) { _, _ ->
                    //dismissed
                }
                builder
                    .create()
                    .show()
            }
        }
    }
}