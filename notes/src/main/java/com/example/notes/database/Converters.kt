package com.example.notes.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun fromLocalDateTime(value: Long?): LocalDateTime? {
        return value?.let { time ->
            Instant.ofEpochMilli(time)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }
    }

    @TypeConverter
    fun localDateTimeToTimestamp(localDateTime: LocalDateTime?): Long? {
        return localDateTime?.atZone(ZoneId.systemDefault())
            ?.toInstant()
            ?.toEpochMilli()
    }
}