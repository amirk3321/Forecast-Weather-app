package com.c4coding.forecastweather.data.provider

import com.c4coding.forecastweather.internal.Unit

interface UnitProvider {
    fun getUnitSystem() : Unit
}