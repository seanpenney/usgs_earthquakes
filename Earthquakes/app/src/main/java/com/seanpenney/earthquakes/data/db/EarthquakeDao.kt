package com.seanpenney.earthquakes.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.seanpenney.earthquakes.data.db.entities.EarthquakeData


@Dao
interface EarthquakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(earthquake: EarthquakeData)

    @Delete
    suspend fun delete(earthquake: EarthquakeData)

    @Query("SELECT * FROM earthquakes")
    fun getAllEarthquakes(): LiveData<List<EarthquakeData>>

    @Query("SELECT * FROM earthquakes where earthquake_time >= :time ORDER BY earthquake_time DESC")
    fun getAllEarthquakesSinceTime(time: Long): LiveData<List<EarthquakeData>>

    @Query("SELECT COUNT(earthquake_id) FROM earthquakes")
    fun getDataCount(): LiveData<Int>

    @Query("DELETE FROM earthquakes")
    suspend fun deleteTable()
}