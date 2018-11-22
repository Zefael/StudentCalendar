package com.blueamber.studentcalendar.ui.base.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import android.util.SparseArray
import android.view.ViewGroup

abstract class BasePagerFragmentAdapter<ItemType, FragmentType : Fragment>
    @JvmOverloads constructor(fm: FragmentManager, var data: List<ItemType>? = null) : FragmentStatePagerAdapter(fm) {

    private val mFragments = SparseArray<FragmentType>()

    fun update(data: List<ItemType>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): FragmentType {
        return mFragments.get(position)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        data?.let { mFragments.put(position, buildFragment(position, it[position])) }

        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        super.destroyItem(container, position, any)
        mFragments.remove(position)
    }

    override fun getCount(): Int {
        return data?.size ?: 0
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    protected abstract fun buildFragment(position: Int, item: ItemType): FragmentType

}