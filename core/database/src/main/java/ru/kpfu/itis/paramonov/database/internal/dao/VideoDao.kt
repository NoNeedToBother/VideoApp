package ru.kpfu.itis.paramonov.database.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.kpfu.itis.paramonov.database.internal.entity.VideoEntity
import java.util.Date

@Dao
internal interface VideoDao {

    @Query("SELECT * FROM videos WHERE id IN " +
            "(SELECT id FROM videos WHERE save_date > :after " +
            "GROUP BY youtube_id ORDER BY save_date DESC)" +
            " ORDER BY save_date DESC LIMIT :limit")
    fun getLatestVideos(limit: Int, after: Long): List<VideoEntity>

    @Query("SELECT * FROM videos WHERE youtube_id = :id ORDER BY save_date DESC LIMIT 1")
    fun getLatestVideoByYoutubeId(id: String): VideoEntity

    @Insert
    fun saveVideos(videos: List<VideoEntity>)
}