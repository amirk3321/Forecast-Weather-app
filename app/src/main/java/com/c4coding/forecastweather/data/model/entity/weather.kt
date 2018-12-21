package com.c4coding.forecastweather.data.model.entity

import com.c4coding.forecastweather.data.model.entity.CurrentWeather
import com.c4coding.forecastweather.data.model.entity.Location
import com.google.gson.annotations.SerializedName

data class weather(
    @SerializedName("current")
    val currentWeather: CurrentWeather,
    @SerializedName("location")
    val location: Location
)