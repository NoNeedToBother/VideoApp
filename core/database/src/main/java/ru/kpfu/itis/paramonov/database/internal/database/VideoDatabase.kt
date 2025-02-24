package ru.kpfu.itis.paramonov.database.internal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.kpfu.itis.paramonov.database.internal.dao.VideoDao
import ru.kpfu.itis.paramonov.database.internal.entity.VideoEntity

@Database(
    version = 1,
    entities = [
        VideoEntity::class,
    ]
)
internal abstract class VideoDatabase: RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {
        fun init(context: Context, name: String): VideoDatabase {
            return Room.databaseBuilder(context, VideoDatabase::class.java, name)
                .build()
        }
    }
}
