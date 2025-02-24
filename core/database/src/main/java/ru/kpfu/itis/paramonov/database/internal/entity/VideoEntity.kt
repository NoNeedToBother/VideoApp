package ru.kpfu.itis.paramonov.database.internal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "videos"
)
internal data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "pexels_id")
    val pexelsId: Long,
    val title: String,
    val duration: Int,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String,
    @ColumnInfo(name = "video_url")
    val videoUrl: String,
    val height: Int,
    val width: Int,
    @ColumnInfo(name = "save_date")
    val saveDate: Long = Date().time,
)