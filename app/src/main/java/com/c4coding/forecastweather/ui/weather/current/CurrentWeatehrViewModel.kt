package com.c4coding.forecastweather.ui.weather.current

import androidx.lifecycle.ViewModel;
import com.c4coding.forecastweather.data.provider.UnitProvider
import com.c4coding.forecastweather.data.repository.ForcastRepository
import com.c4coding.forecastweather.internal.Unit
import com.c4coding.forecastweather.internal.lazyDefarred

class CurrentWeatehrViewModel(
    private val forcastRepository: ForcastRepository,
    unitProvider: UnitProvider) : ViewModel() {
    var unitSpacific = unitProvider.getUnitSystem()
    val isMatric : Boolean
    get() = unitSpacific == Unit.METRIC
    val weather by lazyDefarred {
        forcastRepository.getWeather(isMatric)
    }
}
