package com.seanpenney.earthquakes.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seanpenney.earthquakes.data.db.entities.EarthquakeData

@Database(
        entities = [EarthquakeData::class],
        version = 1
)
abstract class EarthquakeDatabase : RoomDatabase() {
    abstract fun getEarthquakeDao(): EarthquakeDao

    companion object {
        // Make sure this is thread safe
        @Volatile
        private var instance: EarthquakeDatabase? = null
        private val LOCK = Any()

        // Make sure this is the only thread accessing this instance
        operator fun invoke(context: Context) = instance
                ?: synchronized(LOCK) {
                    instance
                            ?: createDatabase(
                                    context
                            ).also {
                                instance = it
                            }
                }

        private fun createDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        EarthquakeDatabase::class.java, "EarthquakeDatabase.db").build()
    }
}