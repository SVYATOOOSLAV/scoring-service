package ru.tournament.scoring.internal.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "client.tournament-storage")
data class TournamentStorageProperties(
    val url: String
)
