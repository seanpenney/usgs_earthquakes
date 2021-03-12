package com.seanpenney.earthquakes.utilities

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.seanpenney.earthquakes.data.db.EarthquakeDatabase
import com.seanpenney.earthquakes.data.db.entities.EarthquakeData
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object FetchUsgsData {
    private val TAG = FetchUsgsData::class.qualifiedName

    init {
        println("FetchUsgsData class invoked.")
    }

    public fun fetchEarthquakes(): List<EarthquakeData> {
        val earthquakes = mutableListOf<EarthquakeData>()

        // default endtime is present time and default starttime is NOW - 30 days ago, so we don't need to set those
        val connection =
            URL("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson").openConnection() as HttpURLConnection
        val data = connection.inputStream.bufferedReader().readText()
        val responseAsJsonObject = JSONObject(data)
        val featuresJsonArray = responseAsJsonObject.getJSONArray("features")

        for (i in 0 until featuresJsonArray.length()) {
            try {
                val feature = featuresJsonArray.getJSONObject(i)
                val properties = feature.getJSONObject("properties")

                val mag = properties.getDouble("mag")
                val place = properties.getString("place")
                val time = properties.getLong("time")
                val url = properties.getString("url")
                val tsunami = if (properties.getInt("tsunami") > 0) true else false

                val id = feature.getString("id")

                if (place.isNotEmpty() && !mag.isNaN() && time > 0) {
                    earthquakes.add(EarthquakeData(mag, place, time, url, tsunami, id))

                } else {
                    Log.e(TAG, "Earthquake ${i} had a problem")

                }
            } catch (exception: Exception) {
                Log.e(TAG, "Could not parse item ${i}")
            }


        }

        Log.d(TAG, "Done fetching USGS data up to 30 days")

        Log.d(TAG, "Number of earthquakes: ${earthquakes.size}")

        return earthquakes

    }

}