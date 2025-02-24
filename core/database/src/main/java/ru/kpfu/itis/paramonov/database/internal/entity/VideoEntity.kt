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
    @ColumnInfo(name = "youtube_id")
    val youTubeId: String,
    val title: String,
    val description: String,
    val duration: String,
    @ColumnInfo(name = "view_count")
    val viewCount: Int,
    @ColumnInfo(name = "like_count")
    val likeCount: Int,
    @ColumnInfo(name = "default_thumbnail_url")
    val defaultThumbnailUrl: String,
    @ColumnInfo(name = "standard_thumbnail_url")
    val standardThumbnailUrl: String,
    @ColumnInfo(name = "save_date")
    val saveDate: Long = Date().time,
)