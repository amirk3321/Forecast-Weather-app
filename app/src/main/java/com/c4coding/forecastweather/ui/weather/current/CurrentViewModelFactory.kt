package com.c4coding.forecastweather.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.c4coding.forecastweather.data.provider.UnitProvider
import com.c4coding.forecastweather.data.repository.ForcastRepository

class CurrentViewModelFactory(
    private val forcastRepository: ForcastRepository,
    private val unitProvider: UnitProvider) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatehrViewModel(forcastRepository,unitProvider) as T
    }
}