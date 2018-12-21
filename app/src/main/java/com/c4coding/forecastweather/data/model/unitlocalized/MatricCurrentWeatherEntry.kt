package com.c4coding.forecastweather.data.model.unitlocalized

import androidx.room.ColumnInfo

data class MatricCurrentWeatherEntry(
    @ColumnInfo(name = "tempC")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionUrlIcon: String,
    @ColumnInfo(name = "windKph")
    override val winSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precipMm")
    override val percipitaitionValue: Double,
    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double


) : UnitSpacificWeatherEntry