package com.c4coding.forecastweather.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.c4coding.forecastweather.data.model.entity.CurrentWeather

@Database(
    entities = [CurrentWeather::class],
    version = 1
)
abstract class ForecastDB : RoomDatabase() {
    abstract fun currentWeathreDao(): CurrentWeatherDao

    companion object {

        @Volatile var instence : ForecastDB?=null
        private val Lock =Any()

        operator fun invoke(context : Context) = instence ?: synchronized(Lock){
            instence ?: buildDatabasee(context).also { instence = it }
        }
        private fun buildDatabasee(context : Context) =
                Room.databaseBuilder(context.applicationContext,
                    ForecastDB::class.java,"forecast.db")
                    .build()
    }
}