package com.xingray.recycleradapter

import java.util.concurrent.atomic.AtomicInteger

/**
 * 生成唯一的id
 *
 * @author : leixing
 * @date : 2019/6/5 21:06
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 *
 */
object UniqueId {
    private val id: AtomicInteger = AtomicInteger(0xffff)
    fun get(): Int {
        return id.getAndIncrement()
    }
}