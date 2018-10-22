package com.blueamber.studentcalendar.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blueamber.studentcalendar.modules.Injectable
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

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

    protected abstract fun getLayoutId() : Int

    protected open fun preInit() {}
    protected open fun setupViewModels() {}
    protected open fun setupViews() {}
    protected open fun setupObservers() {}
    protected open fun setupData() {}
    protected open fun onFragmentShown() {}
    protected open fun onFragmentHide() {}

    open fun onBackPressed() = false

}