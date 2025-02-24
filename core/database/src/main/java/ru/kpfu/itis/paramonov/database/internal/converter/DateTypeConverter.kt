package ru.kpfu.itis.paramonov.database.internal.converter

import androidx.room.TypeConverter
import java.util.Date

internal class DateTypeConverter {
    //@TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    //@TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

fun fromLongToDate(value: Long?): Date? {
    return value?.let { Date(it) }
}

fun dateToLong(date: Date?): Long? {
    return date?.time
}