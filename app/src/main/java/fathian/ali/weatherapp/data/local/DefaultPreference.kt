package fathian.ali.weatherapp.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class DefaultPreference @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : Preference {

    override fun storeString(key: String, value: String?) {
        if (value == null) {
            remove(key)
            return
        }
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, Units.METRIC.name)!!
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}