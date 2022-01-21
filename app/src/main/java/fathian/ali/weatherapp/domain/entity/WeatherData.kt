package fathian.ali.weatherapp.domain.entity

data class WeatherData(
    val name: String,
    val temp: Double,
) {
    companion object {
        val Default = WeatherData("", 0.0)
    }
}