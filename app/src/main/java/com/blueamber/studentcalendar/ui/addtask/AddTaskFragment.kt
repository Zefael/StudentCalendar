package com.blueamber.studentcalendar.ui.addtask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.tools.DateUtil
import com.blueamber.studentcalendar.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.add_task_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class AddTaskFragment : BaseDialogFragment(), Injectable {

    private lateinit var viewModel: AddTaskViewModel

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val dialog = AddTaskFragment()
            val args = Bundle()
            dialog.arguments = args

            dialog.show(fragmentManager, "AddTaskFragment")
        }
    }

    override fun getLayoutId(): Int = R.layout.add_task_fragment

    override fun setupViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddTaskViewModel::class.java)
    }

    override fun preInit() {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenDialog)
    }

    override fun setupViews() {
        val window = dialog?.window
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog_close.setOnClickListener {
            dismiss()
        }
        new_event_date.setOnClickListener {
            DatePickerDialog(
                context, dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        new_event_hours_start.setOnClickListener {
            TimePickerDialog(context, TimePickerDialog.OnTimeSetListener(function = { view, hourOfDay, minute ->
                new_event_hours_start.setText(getString(R.string.time_format, hourOfDay, minute))
                viewModel.updateHoursStart(getString(R.string.time_format, hourOfDay, minute))
            }), 0, 0, true).show()
        }
        new_event_hours_end.setOnClickListener {
            TimePickerDialog(context, TimePickerDialog.OnTimeSetListener(function = { view, hourOfDay, minute ->
                new_event_hours_end.setText(getString(R.string.time_format, hourOfDay, minute))
                viewModel.updateHoursEnd(getString(R.string.time_format, hourOfDay, minute))
            }), 0, 0, true).show()
        }
        new_event_save.setOnClickListener {
            if (new_event_title.text.isNullOrEmpty() || new_event_date.text.isNullOrEmpty() ||
                new_event_hours_start.text.isNullOrEmpty() || new_event_hours_end.text.isNullOrEmpty() ||
                new_event_place.text.isNullOrEmpty()) {

            } else {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    private val dateSetListener = object : DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
            val sdf = SimpleDateFormat(DateUtil.PATTERN_DATE, Locale.FRANCE)
            val cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            new_event_date.setText(sdf.format(cal.time))
            viewModel.updateDate(cal)
        }
    }
}