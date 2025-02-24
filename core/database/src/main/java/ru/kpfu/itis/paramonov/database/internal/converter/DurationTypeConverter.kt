package ru.kpfu.itis.paramonov.database.internal.converter

import androidx.room.TypeConverter
import kotlin.time.Duration

internal class DurationTypeConverter {
    //@TypeConverter
    fun fromString(value: String?): Duration? {
        return value?.let { Duration.parseOrNull(it) }
    }

    //@TypeConverter
    fun durationToString(duration: Duration?): String? {
        return duration?.toString()
    }
}

//@TypeConverter
fun fromStringToDuration(value: String): Duration {
    return Duration.parse(value)
}

//@TypeConverter
fun fromDurationToString(duration: Duration): String {
    return duration.toString()
}