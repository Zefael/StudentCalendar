package com.blueamber.studentcalendar.ui.settings

import android.app.TimePickerDialog
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
import com.blueamber.studentcalendar.PrefKeys
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.models.Groups
import com.blueamber.studentcalendar.models.TasksCalendar
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.MainViewModel
import com.blueamber.studentcalendar.ui.base.BaseDialogFragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.util.*


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
            Observer<List<Groups>> { it -> it?.let { updateGroups(it) } })
        viewModel.firstTaskVisible.observe(this,
            Observer<TasksCalendar> { it -> it?.let { updateAlarmClock(it) } })
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
            mainViewModel.cancelAlarmClock(requireContext())
            alarm_switch.isChecked = false
        }
        alarm_switch.setOnCheckedChangeListener { _, isChecked ->
            launch {
                if (isChecked) {
                    withContext(UI) {
                        val hours = Prefs.getInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_HOURS, 1)
                        val minutes = Prefs.getInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_MINUTES, 0)
                        time_ride_fac.setText(getString(R.string.time_format, hours, minutes))
                        next_alarm_set.text =
                                getString(R.string.next_alarm_set, Prefs.getString(PrefKeys.KEY_ALARM_NEXT_CLOCK, ""))
                        alarm_parameter.visibility = View.VISIBLE
                    }
                    viewModel.getFirstTaskVisibleForUpdateAlarm()
                } else {
                    withContext(UI) { alarm_parameter.visibility = View.GONE }
                    mainViewModel.cancelAlarmClock(this@SettingsFragment.requireContext())
                }
            }
        }
        reset_group.setOnClickListener { showAlertReinitGroups() }
        time_ride_fac.setOnClickListener {
            TimePickerDialog(context, TimePickerDialog.OnTimeSetListener(function = { view, hourOfDay, minute ->
                Prefs.putInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_HOURS, hourOfDay)
                Prefs.putInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_MINUTES, minute)
                time_ride_fac.setText(getString(R.string.time_format, hourOfDay, minute))
                next_alarm_set.text =
                        getString(R.string.next_alarm_set, Prefs.getString(PrefKeys.KEY_ALARM_NEXT_CLOCK, ""))
                viewModel.getFirstTaskVisibleForUpdateAlarm()

            }), 0, 0, true).show()
        }

        if (Prefs.getBoolean(PrefKeys.KEY_ALARM_IS_ACTIVATED, false)) {
            alarm_switch.isChecked = true
            val hours = Prefs.getInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_HOURS, 1)
            val minutes = Prefs.getInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_MINUTES, 0)
            time_ride_fac.setText(getString(R.string.time_format, hours, minutes))
            next_alarm_set.text = getString(R.string.next_alarm_set, Prefs.getString(PrefKeys.KEY_ALARM_NEXT_CLOCK, ""))
        }
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
                if (Prefs.getBoolean(PrefKeys.KEY_ALARM_IS_ACTIVATED, false)) {
                    viewModel.getFirstTaskVisibleForUpdateAlarm()
                }
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

    private fun updateAlarmClock(firstTask: TasksCalendar) {
        val hours = Prefs.getInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_HOURS, 1)
        val minute = Prefs.getInt(PrefKeys.KEY_ALARM_PREPARATION_TIME_MINUTES, 0)
        val calendar = Calendar.getInstance()
        calendar.time = firstTask.date
        val hoursStart = Integer.valueOf(firstTask.hourStart.substring(0, 2))
        val minuteStart = Integer.valueOf(firstTask.hourStart.substring(3))

        calendar.add(Calendar.HOUR, (hoursStart - hours))
        calendar.add(Calendar.MINUTE, (minuteStart - minute))
        mainViewModel.updateAlarmClock(requireContext(), calendar)
    }
}