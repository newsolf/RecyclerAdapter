package com.xingray.recycleradapter

import android.view.View
import android.view.ViewGroup

/**
 * 用于记录列表中一个`ViewType`所对应的视图的信息，包括视图构造工厂，`Holder`构造工厂，
 * 用于创建`ViewHolder`及当绑定点击事件等
 *
 * @author : leixing
 * @date : 19-7-14
 * email : leixing1012@qq.com
 *
 */
class ViewSupport<T : Any, VH : ViewHolder<T>>(private val viewType: Int,
                                               private val adapter: RecyclerAdapter,
                                               private val viewFactory: (ViewGroup) -> View,
                                               private val holderFactory: (View) -> VH) {

    constructor(adapter: RecyclerAdapter, viewFactory: (ViewGroup) -> View, holderFactory: (View) -> VH)
            : this(0, adapter, viewFactory, holderFactory)

    private var itemClickListener: ((ViewGroup, Int, T) -> Unit)? = null
    private var initializer: ((VH) -> Unit)? = null

    fun onCreateViewHolder(parent: ViewGroup): ViewHolder<T> {
        val itemView = viewFactory.invoke(parent)
        val holder = holderFactory.invoke(itemView)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            @Suppress("UNCHECKED_CAST")
            itemClickListener?.invoke(parent, position, adapter.items[position] as T)
        }
        initializer?.invoke(holder)
        return holder
    }

    fun addToAdapter(): RecyclerAdapter {
        adapter.addViewSupport(viewType, this)
        return adapter
    }

    fun itemClickListener(listener: ItemClickListener<T>?): ViewSupport<T, VH> {
        listener?.let {
            itemClickListener = listener::onItemClick
        }
        return this
    }

    fun itemClickListener(listener: ((ViewGroup, Int, T) -> Unit)?): ViewSupport<T, VH> {
        itemClickListener = listener
        return this
    }

    fun initializer(initializer: Initializer<VH>?): ViewSupport<T, VH> {
        initializer?.let {
            this.initializer = initializer::initialize
        }
        return this
    }

    fun initializer(initializer: ((VH) -> Unit)?): ViewSupport<T, VH> {
        this.initializer = initializer
        return this
    }
}