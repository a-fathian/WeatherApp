package fathian.ali.weatherapp.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fathian.ali.weatherapp.R
import fathian.ali.weatherapp.domain.entity.WeatherItem

class MainAdapter(
    private val items: List<WeatherItem>,
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val icon: ImageView
        private val name: TextView
        private val value: TextView

        init {
            icon = itemView.findViewById(R.id.weather_item_icon)
            name = itemView.findViewById(R.id.weather_item_name)
            value = itemView.findViewById(R.id.weather_item_value)
        }

        fun bind(item: WeatherItem) {
            icon.setImageResource(item.icon)
            name.text = item.name
            value.text = item.value
        }
    }
}
