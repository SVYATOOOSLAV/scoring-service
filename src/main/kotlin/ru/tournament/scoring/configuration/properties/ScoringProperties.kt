package ru.tournament.scoring.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("sportsmen-place")
data class ScoringProperties(
    val places: Map<Int, Int>,
    val officialPlaces: Map<Int, Int>
) {
}