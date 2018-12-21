package com.c4coding.forecastweather.data.repository

import androidx.lifecycle.LiveData
import com.c4coding.forecastweather.data.model.unitlocalized.UnitSpacificWeatherEntry

interface ForcastRepository {
    suspend fun getWeather(matrix : Boolean) : LiveData<out UnitSpacificWeatherEntry>
}