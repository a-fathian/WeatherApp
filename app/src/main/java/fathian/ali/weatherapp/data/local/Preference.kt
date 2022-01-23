package fathian.ali.weatherapp.data.local

interface Preference {

    fun storeString(key: String, value: String?)

    fun getString(key: String): String

    fun remove(key: String)
}