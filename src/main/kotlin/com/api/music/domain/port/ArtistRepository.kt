package com.api.music.domain.port

import com.api.music.domain.model.Artist
import com.api.music.domain.model.Album
import com.api.music.domain.model.Track
import java.util.UUID

interface ArtistRepository {
    suspend fun create(name: String, genre: String?): Artist
    suspend fun findAll(): List<Artist>
    suspend fun findById(id: UUID): Artist?
    suspend fun delete(id: UUID): Boolean

    // Para GET artista con relaciones
    suspend fun findByIdWithRelations(id: UUID): ArtistWithRelations?

    data class ArtistWithRelations(
        val artist: Artist,
        val albums: List<Album>,
        val tracks: List<Track>
    )
}