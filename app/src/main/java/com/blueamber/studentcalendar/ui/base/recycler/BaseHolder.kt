package com.blueamber.studentcalendar.ui.base.recycler

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

abstract class BaseHolder<DataType> : RecyclerView.ViewHolder, LayoutContainer {

    constructor(view: View) : super(view)
    constructor(parent: ViewGroup, @LayoutRes layoutId: Int) : this(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))

    val context: Context
        get() = itemView.context

    // used for LayoutContainer
    override val containerView: View?
        get() = itemView

    protected var data: DataType? = null

    fun bind(data: DataType) {
        this.data = data
    }

    open fun display() {}

}