package com.seanpenney.earthquakes.ui.earthquakelist

import com.seanpenney.earthquakes.data.db.entities.EarthquakeData

interface FetchUsgsListener {
    fun onDataFetched(size: Int)
}