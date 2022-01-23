package fathian.ali.weatherapp.di

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.viewbinding.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fathian.ali.weatherapp.common.Constants
import fathian.ali.weatherapp.data.local.DefaultPreference
import fathian.ali.weatherapp.data.local.Preference
import fathian.ali.weatherapp.data.remote.NetworkDataSource
import fathian.ali.weatherapp.data.remote.NetworkDataSourceImpl
import fathian.ali.weatherapp.data.remote.WeatherApi
import fathian.ali.weatherapp.data.repository.WeatherRepositoryImpl
import fathian.ali.weatherapp.domain.repository.WeatherRepository
import fathian.ali.weatherapp.domain.use_case.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository {
        return repository
    }

    @Provides
    fun provideNetworkDataSource(network: NetworkDataSourceImpl): NetworkDataSource {
        return network
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor { message ->
            Log.d("okHttpLog", message)
        }.apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BASIC
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(client: OkHttpClient): WeatherApi {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    fun provideDefaultPreference(
        application: Application
    ): Preference {
        return DefaultPreference(PreferenceManager.getDefaultSharedPreferences(application))
    }

    @Provides
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCaseImpl(repository)
    }

    @Provides
    fun provideGetUnitUseCase(repository: WeatherRepository): GetUnitUseCase {
        return GetUnitUseCaseImpl(repository)
    }

    @Provides
    fun provideSetUnitUseCase(repository: WeatherRepository): SetUnitUseCase {
        return SetUnitUseCaseImpl(repository)
    }

}