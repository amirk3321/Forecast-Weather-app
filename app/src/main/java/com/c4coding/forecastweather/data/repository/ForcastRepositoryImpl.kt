package com.c4coding.forecastweather.data.repository

import androidx.lifecycle.LiveData
import com.c4coding.forecastweather.data.db.CurrentWeatherDao
import com.c4coding.forecastweather.data.model.entity.CurrentWeather
import com.c4coding.forecastweather.data.model.unitlocalized.UnitSpacificWeatherEntry
import com.c4coding.forecastweather.data.network.NetWorkWeatherSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForcastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
   private val netWorkWeatherSource: NetWorkWeatherSource
) : ForcastRepository {

    init {
        netWorkWeatherSource.downloadCurrentWeather.observeForever {newCurrentWeather ->
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
     private fun presist(currentWeather: CurrentWeather){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(currentWeather)
        }
    }

    private suspend fun initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }
    suspend fun fetchCurrentWeather(){
        netWorkWeatherSource.fatchCurrentWeather(
            "karachi",
            Locale.getDefault().language
        )
    }
    private fun isFetchCurrentNeeded(lastFetchTime : ZonedDateTime) : Boolean{
        val thirtyMinute =ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinute)
    }
}