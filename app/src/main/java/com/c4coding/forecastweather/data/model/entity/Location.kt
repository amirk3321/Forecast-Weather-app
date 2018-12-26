package com.c4coding.forecastweather.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val PRIMARY_KEY_LOCATION=0
@Entity(tableName = "location_")
data class Location(
    @SerializedName("country")
    val country: String,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("localtime")
    val localtime: String,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("name")
    val name: String,
    @SerializedName("region")
    val region: String,
    @SerializedName("tz_id")
    val tzId: String
){
    @PrimaryKey(autoGenerate = false)
    var ID:Int= PRIMARY_KEY_LOCATION
    val zonedDateTime :ZonedDateTime
    get() {
        val instant= Instant.ofEpochSecond(localtimeEpoch)
        val zoneId=ZoneId.of(tzId)
        return ZonedDateTime.ofInstant(instant,zoneId)
    }
}