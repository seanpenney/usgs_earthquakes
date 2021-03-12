package com.seanpenney.earthquakes.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.seanpenney.earthquakes.R
import com.seanpenney.earthquakes.data.db.entities.EarthquakeData
import com.seanpenney.earthquakes.ui.earthquakelist.EarthquakeViewModel
import kotlinx.android.synthetic.main.earthquake_text.view.*
import java.util.*


class EarthquakeAdapter(
        var earthquakes: List<EarthquakeData>,
        private val viewModel: EarthquakeViewModel
) : RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder>() {


    inner class EarthquakeViewHolder(earthquakeView: View) : RecyclerView.ViewHolder(earthquakeView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthquakeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.earthquake_text, parent, false)
        return EarthquakeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return earthquakes.size
    }

    override fun onBindViewHolder(holder: EarthquakeViewHolder, position: Int) {
        val currentEarthquake = earthquakes[position]


        if (currentEarthquake.place.isNotEmpty() && !currentEarthquake.mag.isNaN() && currentEarthquake.time > 0) {
            holder.itemView.earthquakeString.text = "Earthquake occured at ${currentEarthquake.place} with magnitude ${currentEarthquake.mag} at time ${getDateString(currentEarthquake.time)}"
            holder.itemView.setOnClickListener {
                val url = currentEarthquake.url
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                val context = it.context
                context.startActivity(i)
            }

        } else {
            holder.itemView.earthquakeString.text = "Problem loading this earthquakes data"
        }
    }

    private fun getDateString(time: Long): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = java.util.Date(time)
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
        return sdf.format(date)

    }

}