package com.c4coding.forecastweather.data.model.unitlocalized

import androidx.room.ColumnInfo

data class ImperialCurrentWeatherEntry(
    @ColumnInfo(name = "tempF")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionUrlIcon: String,
    @ColumnInfo(name = "windMph")
    override val winSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precipIn")
    override val percipitaitionValue: Double,
    @ColumnInfo(name = "feelslikeF")
    override val feelsLikeTemperature: Double


) : UnitSpacificWeatherEntry