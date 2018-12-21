package com.c4coding.forecastweather.data.model.unitlocalized

interface UnitSpacificWeatherEntry {
    val temperature :Double
    val conditionText : String
    val conditionUrlIcon :String
    val winSpeed : Double
    val windDirection :String
    val percipitaitionValue : Double
    val feelsLikeTemperature :Double
}