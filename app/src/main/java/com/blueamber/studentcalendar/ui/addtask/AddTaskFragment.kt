package com.blueamber.studentcalendar.ui.addtask

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.add_task_fragment.*

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

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}