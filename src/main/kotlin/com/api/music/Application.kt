package com.api.music

import com.api.music.application.album.AlbumService
import com.api.music.application.artist.ArtistService
import com.api.music.application.track.TrackService
import com.api.music.config.DatabaseFactory
import com.api.music.domain.port.AlbumRepository
import com.api.music.domain.port.ArtistRepository
import com.api.music.domain.port.TrackRepository
import com.api.music.infrastructure.persistence.AlbumRepositoryImpl
import com.api.music.infrastructure.persistence.ArtistRepositoryImpl
import com.api.music.infrastructure.persistence.TrackRepositoryImpl
import com.api.music.infrastructure.web.routing.albumRoutes
import com.api.music.infrastructure.web.routing.artistRoutes
import com.api.music.infrastructure.web.routing.trackRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.slf4j.event.Level

fun main(args: Array<String>): Unit =
    EngineMain.main(args)

fun Application.module() {

    // DB
    DatabaseFactory.init()

    // Inyección “manual” (hexagonal simple)
    val artistRepository: ArtistRepository = ArtistRepositoryImpl()
    val albumRepository: AlbumRepository = AlbumRepositoryImpl()
    val trackRepository: TrackRepository = TrackRepositoryImpl()

    val artistService = ArtistService(artistRepository)
    val albumService = AlbumService(albumRepository)
    val trackService = TrackService(trackRepository)

    install(CallLogging) {
        level = Level.INFO
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                encodeDefaults = true
            }
        )
    }

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(
                text = "Error interno: ${cause.message}",
                status = io.ktor.http.HttpStatusCode.InternalServerError
            )
            throw cause
        }
    }

    routing {
        route("/api") {
            artistRoutes(artistService)
            albumRoutes(albumService)
            trackRoutes(trackService)
        }
    }
}