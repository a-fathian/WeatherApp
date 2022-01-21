package fathian.ali.weatherapp.data.remote.dto


import com.squareup.moshi.Json

data class Coord(
    @Json(name = "lat") val lat: Double,
    @Json(name = "lon") val lon: Double
)