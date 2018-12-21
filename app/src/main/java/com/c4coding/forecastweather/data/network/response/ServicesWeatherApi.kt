package com.c4coding.forecastweather.data.network.response


import com.c4coding.forecastweather.data.model.entity.weather
import com.c4coding.forecastweather.data.network.ConnectionIntercepter
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "ca021895af4445d192e95313181212"
const val BASE_URL = "http://api.apixu.com/v1/"

//http://api.apixu.com/v1/current.json?k&ey=ca021895af4445d192e95313181212q=karachi&lang=en
interface ServicesWeatherApi {

    @GET("current.json")
    fun getDetails(
        @Query("q") city: String,
        @Query("lang") lang: String = "en"
    ): Deferred<weather>

    companion object {
        operator fun invoke(connectionIntercepter: ConnectionIntercepter): ServicesWeatherApi {
            val interceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(connectionIntercepter)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServicesWeatherApi::class.java)

        }
    }
}