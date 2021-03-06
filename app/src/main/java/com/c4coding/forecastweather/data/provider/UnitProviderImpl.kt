package com.c4coding.forecastweather.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.c4coding.forecastweather.internal.Unit

const val UNIT_LIST_PREFERENCE ="UNIT_LIST_PREFERENCE"
class UnitProviderImpl(context: Context) :PreferenceProvider(context), UnitProvider {
    private val appContext=context

    override fun getUnitSystem(): Unit {
        val unitName=preference.getString(UNIT_LIST_PREFERENCE,Unit.METRIC.name)
        return Unit.valueOf(unitName!!)
    }
}