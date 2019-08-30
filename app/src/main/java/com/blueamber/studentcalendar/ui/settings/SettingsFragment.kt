package com.blueamber.studentcalendar.ui.settings

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blueamber.studentcalendar.PrefKeys.MASTER_SELECTED_IS_ONE
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.PrimaryGroups
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.MainViewModel
import com.blueamber.studentcalendar.ui.base.BaseDialogFragment
import com.enterprise.baseproject.extensions.toast
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : BaseDialogFragment(), Injectable {

    companion object {
        fun show(fragmentManager: FragmentManager, mainViewModel: MainViewModel) {
            val dialog = SettingsFragment()
            val args = Bundle()
            dialog.arguments = args
            dialog.mainViewModel = mainViewModel

            dialog.show(fragmentManager, "SettingsFragment")
        }
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun getLayoutId(): Int = R.layout.settings_fragment

    override fun setupViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)
    }

    override fun setupObservers() {
        viewModel.groups.observe(this,
            Observer<List<Groups>> { it?.let { updateGroups(it) } })
        viewModel.primaryGroups.observe(this,
            Observer<List<PrimaryGroups>> { it?.let { updatePrimaryGroups(it) } })
    }

    override fun preInit() {
        setStyle(STYLE_NORMAL, R.style.FullscreenDialog)
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
        reset_group.setOnClickListener { showAlertReinitGroups() }

        val dataMaster = Prefs.getBoolean(MASTER_SELECTED_IS_ONE, true)
        master_1.isChecked = dataMaster
        master_2.isChecked = !dataMaster
        master_1.setOnClickListener { view -> onMasterSelected(view) }
        master_2.setOnClickListener { view -> onMasterSelected(view) }
    }

    fun onMasterSelected(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            if (checked) {
                when (view.getId()) {
                    R.id.master_1 -> {
                        Prefs.putBoolean(MASTER_SELECTED_IS_ONE, true)
                        R.string.settings_select_master1_message.toast(requireContext(), Toast.LENGTH_LONG)
                    }
                    R.id.master_2 -> {
                        Prefs.putBoolean(MASTER_SELECTED_IS_ONE, false)
                        R.string.settings_select_master2_message.toast(requireContext(), Toast.LENGTH_LONG)
                    }
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun setupData() {
        viewModel.downloadGroups()
    }

    private fun updateGroups(groups: List<Groups>) {
        list_group.removeAllViews()
        groups.forEach {
            val itemGroups = layoutInflater.inflate(R.layout.settings_group_item, null)
            val switcher = itemGroups.findViewById<SwitchMaterial>(R.id.group_switch)
            val originalGroup = itemGroups.findViewById<TextView>(R.id.group_original_name)
            val newGroup = itemGroups.findViewById<TextInputEditText>(R.id.group_new_name)

            switcher.isChecked = it.visibility
            originalGroup.text = it.originalGroups
            newGroup.setText(it.newGroups)

            switcher.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateVisibilityGroup(isChecked, it.originalGroups)
            }

            newGroup.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty()) {
                        viewModel.updateNewGroup(s.toString(), it.originalGroups)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            list_group.addView(itemGroups)
        }
    }

    private fun updatePrimaryGroups(primaryGroups: List<PrimaryGroups>) {
        list_primary_group.removeAllViews()
        primaryGroups.forEach {
            val itemGroups = layoutInflater.inflate(R.layout.settings_group_item, null)
            val switcher = itemGroups.findViewById<SwitchMaterial>(R.id.group_switch)
            val originalGroup = itemGroups.findViewById<TextView>(R.id.group_original_name)
            val newGroup = itemGroups.findViewById<TextInputEditText>(R.id.group_new_name)

            switcher.isChecked = it.visibility
            originalGroup.text = it.originalPrimaryGroup
            newGroup.setText(it.newPrimaryGroup)

            switcher.setOnCheckedChangeListener { _, isChecked ->
                viewModel.updateVisibilityPrimaryGroup(isChecked, it.originalPrimaryGroup)
            }

            newGroup.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (!s.isNullOrEmpty()) {
                        viewModel.updateNewPrimaryGroup(s.toString(), it.originalPrimaryGroup)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
            list_primary_group.addView(itemGroups)
        }
    }

    private fun showAlertReinitGroups() {
        activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.dialog_reset_group_title)
                setMessage(R.string.dialog_reset_group_message)
                setPositiveButton(R.string.reset) { _, _ ->
                    viewModel.resetAllGroups()
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