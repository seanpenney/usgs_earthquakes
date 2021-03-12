package com.seanpenney.earthquakes.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "earthquakes")
data class EarthquakeData(
        @ColumnInfo(name = "earthquake_mag")
        var mag: Double,
        @ColumnInfo(name = "earthquake_place")
        var place: String,
        @ColumnInfo(name = "earthquake_time")
        var time: Long,
        @ColumnInfo(name = "earthquake_url")
        var url: String,
        @ColumnInfo(name = "earthquake_tsunami")
        var tsunami: Boolean,
        @PrimaryKey
        @ColumnInfo(name = "earthquake_id")
        var id: String


) {
}