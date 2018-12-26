package com.c4coding.forecastweather.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c4coding.forecastweather.data.model.entity.CurrentWeather
import com.c4coding.forecastweather.data.model.entity.weather
import com.c4coding.forecastweather.data.network.response.ServicesWeatherApi
import com.c4coding.forecastweather.internal.NoConnectivityExecption

class NetWorkWeatherSourceImpl(
   private val servicesWeatherApi : ServicesWeatherApi
) : NetWorkWeatherSource {
    private val _downloadCurrentWeather =MutableLiveData<weather>()
    override val downloadCurrentWeather: LiveData<weather>
        get() = _downloadCurrentWeather

    override suspend fun fatchCurrentWeather(city: String, language: String) {
        try {
            val fetchRespnse=servicesWeatherApi
                .getDetails(city,language).await()

            _downloadCurrentWeather.postValue(fetchRespnse)
        }catch (e : NoConnectivityExecption){
            Log.e("Connection","no internet connection",e)
        }
    }
}