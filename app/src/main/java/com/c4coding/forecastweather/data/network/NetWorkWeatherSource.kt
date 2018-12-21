package com.c4coding.forecastweather.data.network

import androidx.lifecycle.LiveData
import com.c4coding.forecastweather.data.model.entity.CurrentWeather
import java.util.*

interface NetWorkWeatherSource {
     val downloadCurrentWeather : LiveData<CurrentWeather>
     suspend fun  fatchCurrentWeather(city: String,Language : String)
}