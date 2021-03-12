package com.seanpenney.earthquakes.ui.earthquakelist

import android.R.attr.duration
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.seanpenney.earthquakes.R
import com.seanpenney.earthquakes.adapters.EarthquakeAdapter
import com.seanpenney.earthquakes.data.db.EarthquakeDatabase
import com.seanpenney.earthquakes.data.repositories.EarthquakeRepository
import kotlinx.android.synthetic.main.activity_earthquake.*


class EarthquakeActivity : AppCompatActivity() {
    private val TAG = EarthquakeActivity::class.qualifiedName



    lateinit var database: EarthquakeDatabase
    lateinit var repository: EarthquakeRepository
    lateinit var factory: EarthquakeViewModelFactory
    lateinit var viewModel: EarthquakeViewModel
    lateinit var adapter: EarthquakeAdapter
    var fetchedEarthquakeSize : Int = -1
    var earthquakeDatabaseSize : Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earthquake)

        database = EarthquakeDatabase(this)
        repository = EarthquakeRepository(database)
        factory = EarthquakeViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this, factory).get(EarthquakeViewModel::class.java)

        adapter = EarthquakeAdapter(listOf(), viewModel)

        recyclerViewEarthquakes.layoutManager = LinearLayoutManager(this)
        recyclerViewEarthquakes.adapter = adapter





        btnSubmitSearch.setOnClickListener {
            var days: Long
            try {
                days = editTextTextPersonName.text.toString().toLong()

            } catch (exception: Exception) {
                Toast.makeText(
                        this,
                        "Please enter valid number of days to search for",
                        Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener

            }
            if (days < 1 || days > 30) {
                Toast.makeText(this, "Enter number between 1 through 30", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.deleteTable()

            fetchedEarthquakeSize = -1
            earthquakeDatabaseSize = -2

            viewModel.fetchAllEarthquakesUsgs(object : FetchUsgsListener {
                override fun onDataFetched(size: Int) {
                    Log.d(TAG, "Fetched size is: ${size}")
                    fetchedEarthquakeSize = size
                }

            }, System.currentTimeMillis() - (days * 86400000))

            viewModel.getDataCount().observe(this@EarthquakeActivity, Observer {
                Log.d(TAG, "Earthquake db updated size: ${it}")
                earthquakeDatabaseSize = it
            }
            )

            val progress = ProgressDialog(this@EarthquakeActivity)
            progress.setTitle("Loading")
            progress.setMessage("Wait while loading...")
            progress.setCancelable(false);
            progress.show()

            Thread(Runnable {
                while (fetchedEarthquakeSize != earthquakeDatabaseSize) {

                }
                progress.dismiss();
                Log.d(TAG, "Data loaded")

                runOnUiThread {
                    Toast.makeText(this, "Searching through last ${days} days. You can click on the earthquake item to go to the USGS page for it", Toast.LENGTH_LONG).show()
                    viewModel.getAllEarthquakesSinceTime(System.currentTimeMillis() - (days * 86400000))
                            .observe(this@EarthquakeActivity, Observer {
                                adapter.earthquakes = it
                                adapter.notifyDataSetChanged()
                            })
                }

            }).start()
        }


    }




}