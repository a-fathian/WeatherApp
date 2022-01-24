package fathian.ali.weatherapp.data.remote

import com.google.common.truth.Truth.assertThat
import fathian.ali.weatherapp.common.Constants
import fathian.ali.weatherapp.common.Either
import fathian.ali.weatherapp.data.local.Units
import fathian.ali.weatherapp.data.remote.dto.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.given
import retrofit2.HttpException

class NetworkDataSourceImplTest {

    private lateinit var api: WeatherApi

    private lateinit var network: NetworkDataSource

    @Before
    fun setUp() {
        api = Mockito.mock(WeatherApi::class.java)
        network = NetworkDataSourceImpl(api)
    }

    @Test
    fun `test getWeatherInformation returns success`() = runBlocking {
        val weatherDto =
            WeatherDto("", Clouds(100), 200, Coord(0.0, 0.0), 0, 0, Main(0.0, 0, 0, 0.0, 0.0, 0.0),
                "", Sys("", 0, 0, 0, 0), 0, 0, emptyList(), Wind(0, 0.0))
        `when`(api.getWeatherInformation("tabriz", Constants.API_KEY_VALUE, Units.METRIC.name))
            .thenReturn(weatherDto)
        val result = network.getWeatherInformation("tabriz", Units.METRIC.name)
        assertThat(result).isInstanceOf(Either.Success::class.java)
        assertThat((result as Either.Success).data).isEqualTo(weatherDto)
    }

    @Test
    fun `test getWeatherInformation returns error`() = runBlocking {
        given(api.getWeatherInformation("tabriz", Constants.API_KEY_VALUE, Units.METRIC.name))
            .willAnswer {
                throw Exception("error")
            }
        val result = network.getWeatherInformation("tabriz", Units.METRIC.name)
        assertThat(result).isInstanceOf(Either.Error::class.java)
        assertThat((result as Either.Error).error).isEqualTo("error")
    }
}