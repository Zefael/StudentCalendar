package com.blueamber.studentcalendar.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
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

    private fun currentFragment() : Fragment? = fragmentManager.findFragmentByTag((fragmentManager.backStackEntryCount - 1).toString())

    fun navigate(to: Fragment) {
        val from = currentFragment()

        if (from?.tag != to.tag) {
            if (from != null) from.userVisibleHint = false

            val transaction = fragmentManager.beginTransaction()
            if (fragmentManager.backStackEntryCount > 0) {
                transaction.setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )
            }
            transaction.add(containerId, to, fragmentManager.backStackEntryCount.toString())
            if (addToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }
    }

    fun handleBackPressed(defaultAction:(isLastFragment: Boolean) -> Unit) {
        val currentFragment = currentFragment()
        if(currentFragment !is BaseFragment || !currentFragment.onBackPressed()) {
            defaultAction(fragmentManager.backStackEntryCount == 1)
        }
    }

}