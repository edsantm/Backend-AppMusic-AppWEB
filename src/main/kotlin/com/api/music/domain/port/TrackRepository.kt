package com.api.music.domain.port

import com.api.music.domain.model.Track
import java.util.UUID

interface TrackRepository {
    suspend fun create(title: String, duration: Int, albumId: UUID): Track
    suspend fun findAll(): List<Track>
    suspend fun findById(id: UUID): Track?
    suspend fun delete(id: UUID): Boolean
}