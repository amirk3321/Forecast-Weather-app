package com.c4coding.forecastweather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.c4coding.forecastweather.data.model.entity.CurrentWeather
import com.c4coding.forecastweather.data.model.entity.PRIMARY_CONSTENT_KEY_WEATHER
import com.c4coding.forecastweather.data.model.unitlocalized.ImperialCurrentWeatherEntry
import com.c4coding.forecastweather.data.model.unitlocalized.MatricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentWeather: CurrentWeather)

    @Query("SELECT * FROM current_weather WHERE id = $PRIMARY_CONSTENT_KEY_WEATHER")
    fun getWeatherMetric(): LiveData<MatricCurrentWeatherEntry>

    @Query("SELECT * FROM current_weather WHERE id = $PRIMARY_CONSTENT_KEY_WEATHER")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}