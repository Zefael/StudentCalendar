package com.blueamber.studentcalendar.ui.base

import androidx.lifecycle.ViewModelProvider
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.blueamber.studentcalendar.modules.Injectable
import com.blueamber.studentcalendar.ui.MainViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseDialogFragment : DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var mainViewModel : MainViewModel

    override fun onAttach(context: Context) {
        if(this is Injectable) {
            AndroidSupportInjection.inject(this)
        }
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        preInit()

        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViewModels()
        setupViews()
        setupObservers()
        setupData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if(isVisibleToUser) { onFragmentShown() }
        else { onFragmentHide() }
    }

    protected open fun setupViewModels() {
        mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel::class.java)
    }

    protected abstract fun getLayoutId() : Int

    protected open fun preInit() {}
    protected open fun setupViews() {}
    protected open fun setupObservers() {}
    protected open fun setupData() {}
    protected open fun onFragmentShown() {}
    protected open fun onFragmentHide() {}

    open fun onBackPressed() = false
}