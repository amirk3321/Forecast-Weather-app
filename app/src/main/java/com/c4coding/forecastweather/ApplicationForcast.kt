package com.c4coding.forecastweather

import android.app.Application
import android.content.Context
import com.c4coding.forecastweather.data.db.ForecastDB
import com.c4coding.forecastweather.data.network.ConnectionIntercepter
import com.c4coding.forecastweather.data.network.ConnectionIntercepterImpl
import com.c4coding.forecastweather.data.network.NetWorkWeatherSource
import com.c4coding.forecastweather.data.network.NetWorkWeatherSourceImpl
import com.c4coding.forecastweather.data.network.response.ServicesWeatherApi
import com.c4coding.forecastweather.data.provider.LocationProvider
import com.c4coding.forecastweather.data.provider.LocationProviderImpl
import com.c4coding.forecastweather.data.provider.UnitProvider
import com.c4coding.forecastweather.data.provider.UnitProviderImpl
import com.c4coding.forecastweather.data.repository.ForcastRepository
import com.c4coding.forecastweather.data.repository.ForcastRepositoryImpl
import com.c4coding.forecastweather.ui.weather.current.CurrentViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ApplicationForcast : Application() , KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ApplicationForcast))

        bind() from singleton { ForecastDB(instance()) }
        bind() from singleton { instance<ForecastDB>().currentWeathreDao() }
        bind() from singleton { instance<ForecastDB>().currentLocationDao() }
        bind<ConnectionIntercepter>() with singleton { ConnectionIntercepterImpl(instance()) }
        bind() from singleton { ServicesWeatherApi(instance()) }
        bind<NetWorkWeatherSource>() with singleton { NetWorkWeatherSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(),instance()) }
        bind<ForcastRepository>() with singleton { ForcastRepositoryImpl(instance(),instance(),instance(),instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentViewModelFactory(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}