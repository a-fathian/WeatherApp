package fathian.ali.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg") val deg: Int,
    @SerializedName("speed") val speed: Double
)