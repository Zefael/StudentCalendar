package com.blueamber.studentcalendar.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.blueamber.studentcalendar.R
import com.blueamber.studentcalendar.ui.base.BaseFragment

class NavigationDelegate(private val fragmentManager: FragmentManager,
                         private val containerId: Int,
                         private val addToBackStack: Boolean = true) {

    init {
        fragmentManager.addOnBackStackChangedListener {
            currentFragment()?.userVisibleHint = true
        }
    }

    fun currentFragment() : Fragment? = fragmentManager.findFragmentByTag((fragmentManager.backStackEntryCount - 1).toString())

    fun navigate(to: Fragment) {
        val from = currentFragment()
        if(from != null) from.userVisibleHint = false

        val transaction = fragmentManager.beginTransaction()
        if(fragmentManager.backStackEntryCount > 0) {
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
        }
        transaction.replace(containerId, to, fragmentManager.backStackEntryCount.toString()).commit()
    }

    fun replace(fragment: Fragment) {
        fragmentManager.beginTransaction().replace(containerId, fragment).commit()
    }

    fun handleBackPressed(defaultAction:(isLastFragment: Boolean) -> Unit) {
        val currentFragment = currentFragment()
        if(currentFragment !is BaseFragment || !currentFragment.onBackPressed()) {
            defaultAction(fragmentManager.backStackEntryCount == 1)
        }
    }

}