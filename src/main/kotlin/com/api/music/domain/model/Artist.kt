package com.api.music.domain.model

import java.util.UUID

data class Artist(
    val id: UUID,
    val name: String,
    val genre: String?,
)
