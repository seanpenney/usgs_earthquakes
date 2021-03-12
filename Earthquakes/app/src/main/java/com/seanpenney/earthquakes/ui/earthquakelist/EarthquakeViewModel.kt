package com.seanpenney.earthquakes.ui.earthquakelist

import androidx.lifecycle.ViewModel
import com.seanpenney.earthquakes.data.db.entities.EarthquakeData
import com.seanpenney.earthquakes.data.repositories.EarthquakeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EarthquakeViewModel(
        private val repository: EarthquakeRepository
) : ViewModel() {

    // Ok to run on Main thread because Room provides thread safety
    fun upsert(earthquake: EarthquakeData) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert(earthquake)
    }

    // Ok to run on Main thread because Room provides thread safety
    fun delete(earthquake: EarthquakeData) = CoroutineScope(Dispatchers.Main).launch {
        repository.delete(earthquake)
    }

    fun getAllEarthquakes() = repository.getAllEarthquakes()

    fun getDataCount() = repository.getDataCount()

    fun deleteTable() = CoroutineScope(Dispatchers.Main).launch {
        repository.deleteTable()
    }

    fun getAllEarthquakesSinceTime(time: Long) = repository.getAllEarthquakesSinceTime(time)


    fun fetchAllEarthquakesUsgs(fetchUsgsListener: FetchUsgsListener, startTime: Long) = CoroutineScope(Dispatchers.IO).launch {
        repository.fetchAllEarthquakesUsgs(fetchUsgsListener, startTime)
    }

}