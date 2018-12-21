package com.c4coding.forecastweather.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.c4coding.forecastweather.data.model.entity.CurrentWeather
import com.c4coding.forecastweather.data.network.response.ServicesWeatherApi
import com.c4coding.forecastweather.internal.NoConnectivityExecption

class NetWorkWeatherSourceImpl(
   private val servicesWeatherApi : ServicesWeatherApi
) : NetWorkWeatherSource {
    private val _downloadCurrentWeather =MutableLiveData<CurrentWeather>()
    override val downloadCurrentWeather: LiveData<CurrentWeather>
        get() = _downloadCurrentWeather

    override suspend fun fatchCurrentWeather(city: String, language: String) {
        try {
            val fetchRespnse=servicesWeatherApi.getDetails(city,language).await()
            _downloadCurrentWeather.postValue(fetchRespnse.currentWeather)
        }catch (e : NoConnectivityExecption){
            Log.e("Connection","no internet connection",e)
        }
    }
}