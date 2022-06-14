package com.aire.android.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @author ZhuPeipei
 * @date 2022/5/4 01:11
 */
object CoroutinesMain {
    @JvmStatic
    fun main(args: Array<String>) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            log("111111111 ${Thread.currentThread().name}")
            withContext(Dispatchers.IO) {
                delay(1000)
                log("22222222222 ${Thread.currentThread().name}")
            }

            delay(10)
            log("3333333333 ${Thread.currentThread().name}")
        }

        log("4444444444 ${Thread.currentThread().name}")
        Thread.sleep(2000)
        log("5555555555")

//        GlobalScope.launch(context = Dispatchers.IO) {
//            //延时一秒
//            delay(1000)
//            log("launch")
//        }
//        //主动休眠两秒，防止 JVM 过快退出
//        Thread.sleep(2000)
//        log("end")
    }

    private fun log(msg: Any?) = println("[${Thread.currentThread().name}] $msg")
}