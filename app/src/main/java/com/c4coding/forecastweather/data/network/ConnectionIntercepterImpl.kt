package com.c4coding.forecastweather.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.c4coding.forecastweather.internal.NoConnectivityExecption
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionIntercepterImpl(context: Context) : ConnectionIntercepter {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityExecption()
        return chain.proceed(chain.request())
    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}