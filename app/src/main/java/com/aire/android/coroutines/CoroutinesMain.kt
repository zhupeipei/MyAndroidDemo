package com.aire.android.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author ZhuPeipei
 * @date 2022/5/4 01:11
 */
object CoroutinesMain {
    @JvmStatic
    fun main(args: Array<String>) {
        GlobalScope.launch(context = Dispatchers.IO) {
            //延时一秒
            delay(1000)
            log("launch")
        }
        //主动休眠两秒，防止 JVM 过快退出
        Thread.sleep(2000)
        log("end")
    }

    private fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")
}