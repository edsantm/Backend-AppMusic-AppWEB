package com.api.music.infrastructure.web.dto

import com.api.music.domain.model.Artist
import kotlinx.serialization.Serializable

@Serializable
data class CreateArtistRequest(
    val name: String,
    val genre: String? = null
)

@Serializable
data class ArtistResponse(
    val id: String,
    val name: String,
    val genre: String? = null
)

fun Artist.toResponse() = ArtistResponse(
    id = id.toString(),
    name = name,
    genre = genre
)

@Serializable
data class ArtistWithRelationsResponse(
    val id: String,
    val name: String,
    val genre: String? = null,
    val albums: List<AlbumResponse>,
    val tracks: List<TrackResponse>
)
