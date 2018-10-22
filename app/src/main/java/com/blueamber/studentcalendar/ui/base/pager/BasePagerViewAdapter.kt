package com.blueamber.studentcalendar.ui.base.pager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

abstract class BasePagerViewAdapter<ItemType, ViewType : View>(var data: List<ItemType> = emptyList()) : PagerAdapter() {

    private val views = SparseArray<ViewType>()

    fun update(data: List<ItemType>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val view = buildView(collection.context, data[position])
        collection.addView(view)
        views.put(position, view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        views.remove(position)
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    protected abstract fun buildView(context: Context, item: ItemType): ViewType

}