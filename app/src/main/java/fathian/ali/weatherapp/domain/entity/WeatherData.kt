package fathian.ali.weatherapp.domain.entity

data class WeatherData(
    val city: String,
    val country: String,
    val temp: Double,
    val feelsLike: Double,
    val icon: String,
    val description: String,
    val windSpeed: Double,
    val humidity: Int,
    val pressure: Int,
    val visibility: Int,
    val cloudiness: Int,
    val sunrise: Long,
    val sunset: Long,
) {
    companion object {
        val Default = WeatherData("", "", 0.0, 0.0, "", "", 0.0, 0, 0, 0, 0, 0, 0)
    }
}