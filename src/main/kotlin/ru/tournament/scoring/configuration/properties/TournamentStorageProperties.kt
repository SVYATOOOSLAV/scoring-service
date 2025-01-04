package ru.tournament.scoring.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "client.tournament-storage")
data class TournamentStorageProperties(
    val url: String
)
