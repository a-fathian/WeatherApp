package fathian.ali.weatherapp.data.local

enum class Units {
    METRIC, IMPERIAL;

    companion object {
        fun getByName(value: String?): Units {
            values().forEach {
                if (value == it.name) {
                    return it
                }
            }
            return METRIC
        }
    }
}