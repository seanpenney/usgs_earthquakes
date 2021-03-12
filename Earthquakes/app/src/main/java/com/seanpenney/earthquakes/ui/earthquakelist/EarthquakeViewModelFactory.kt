package com.seanpenney.earthquakes.ui.earthquakelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seanpenney.earthquakes.data.repositories.EarthquakeRepository

@Suppress("UNCHECKED_CAST")
class EarthquakeViewModelFactory(
    private val repository: EarthquakeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EarthquakeViewModel(repository) as T
    }
}