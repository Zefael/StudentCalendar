package com.blueamber.studentcalendar.ui.base.recycler

import android.support.v7.widget.RecyclerView

abstract class BaseRecyclerAdapter<DataType> (data: List<DataType> = listOf()) : RecyclerView.Adapter<BaseHolder<DataType>>() {

    private var data: MutableList<DataType>

    init {
        this.data = data.toMutableList()
    }

    fun update(data: List<DataType>, notifyChanges: Boolean = true) {
        this.data = data.toMutableList()
        if(notifyChanges) { notifyDataSetChanged() }
    }

    fun addItems(newData: List<DataType>, notifyChanges: Boolean = true) {
        val startIndex = data.size
        data.addAll(newData)
        if(notifyChanges) { notifyItemRangeInserted(startIndex, newData.size) }
    }

    fun getItem(position: Int): DataType? {
        return if(position in 0 until data.size) data[position] else null
    }

    override fun onBindViewHolder(holder: BaseHolder<DataType>, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
        holder.display()
    }

    override fun getItemCount(): Int {
        return data.size
    }

}