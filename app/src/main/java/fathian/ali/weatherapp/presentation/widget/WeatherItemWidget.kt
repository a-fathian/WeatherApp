package fathian.ali.weatherapp.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import fathian.ali.weatherapp.R
import fathian.ali.weatherapp.domain.entity.WeatherItem

class WeatherItemWidget @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
) : ConstraintLayout(context, attributeSet) {

    private val icon: ImageView
    private val name: TextView
    private val value: TextView

    init {
        icon = findViewById(R.id.weather_item_icon)
        name = findViewById(R.id.weather_item_name)
        value = findViewById(R.id.weather_item_value)
    }

    fun setItem(item: WeatherItem) {
        icon.setImageResource(item.icon)
        name.text = item.name
        value.text = item.value
    }
}
