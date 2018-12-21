
package com.c4coding.forecastweather.internal

import kotlinx.coroutines.*

fun <T> lazyDefarred(block :suspend CoroutineScope.() -> T) : Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}