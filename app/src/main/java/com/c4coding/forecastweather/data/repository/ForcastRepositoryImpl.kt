package com.c4coding.forecastweather.data.repository

import androidx.lifecycle.LiveData
import com.c4coding.forecastweather.data.db.CurrentLocationDao
import com.c4coding.forecastweather.data.db.CurrentWeatherDao
import com.c4coding.forecastweather.data.model.entity.Location
import com.c4coding.forecastweather.data.model.entity.weather
import com.c4coding.forecastweather.data.model.unitlocalized.UnitSpacificWeatherEntry
import com.c4coding.forecastweather.data.network.NetWorkWeatherSource
import com.c4coding.forecastweather.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForcastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val netWorkWeatherSource: NetWorkWeatherSource,
    private val currentLocationDao: CurrentLocationDao,
    private val locationProvider: LocationProvider
    ) : ForcastRepository {

    init {
        netWorkWeatherSource.downloadCurrentWeather.observeForever { newCurrentWeather ->
            presist(newCurrentWeather)
        }
    }

    override suspend fun getWeather(matrix: Boolean): LiveData<out UnitSpacificWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext if (matrix) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    override suspend fun getLocation(): LiveData<Location> {
        return withContext(Dispatchers.IO) {
            return@withContext currentLocationDao.getLocation()
        }
    }

    private fun presist(currentWeather: weather) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(currentWeather.currentWeather)
            currentLocationDao.upsert(currentWeather.location)
        }
    }

    //ZonedDateTime.now().minusHours(1)
    private suspend fun initWeatherData() {
        val lastWeatherLocation = currentLocationDao.getLocation().value
        if (lastWeatherLocation == null
            ||locationProvider.hasLocationChanged(lastWeatherLocation)) {
            fetchCurrentWeather()
            return
        }
        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()
    }

    suspend fun fetchCurrentWeather() {
        netWorkWeatherSource.fatchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinute = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinute)
    }
}