package com.seanpenney.earthquakes.data.repositories

import android.util.Log
import com.seanpenney.earthquakes.data.db.EarthquakeDatabase
import com.seanpenney.earthquakes.data.db.entities.EarthquakeData
import com.seanpenney.earthquakes.ui.earthquakelist.FetchUsgsListener
import com.seanpenney.earthquakes.utilities.FetchUsgsData

class EarthquakeRepository(
        private val db: EarthquakeDatabase
) {
    private val TAG = EarthquakeRepository::class.qualifiedName

    suspend fun insert(earthquake: EarthquakeData) = db.getEarthquakeDao().insert(earthquake)

    suspend fun delete(earthquake: EarthquakeData) = db.getEarthquakeDao().delete(earthquake)

    fun getAllEarthquakes() = db.getEarthquakeDao().getAllEarthquakes()

    fun getDataCount() = db.getEarthquakeDao().getDataCount()

    suspend fun deleteTable() = db.getEarthquakeDao().deleteTable()

    fun getAllEarthquakesSinceTime(time: Long) = db.getEarthquakeDao().getAllEarthquakesSinceTime(time)

    suspend fun fetchAllEarthquakesUsgs(fetchUsgsListener: FetchUsgsListener) {

        try {
            val earthQuakes = FetchUsgsData.fetchEarthquakes()
            fetchUsgsListener.onDataFetched(earthQuakes.size)


            for (earthQuake in earthQuakes) {
                insert(earthQuake)
            }

        } catch (exception: Exception) {
            Log.e(TAG, "Problem getting data from USGS")
        }
    }
}