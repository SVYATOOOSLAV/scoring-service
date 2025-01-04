package ru.tournament.scoring.logic.service.impl.box

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import ru.tournament.scoring.configuration.properties.ScoringProperties
import ru.tournament.scoring.logic.mapper.toNormalFormat
import ru.tournament.scoring.logic.service.CoefficientMakerService
import ru.tournament.storage.dto.SportsmenGamesResponse
import ru.tournament.storage.dto.SportsmenSanctionsResponse
import kotlin.math.pow

@Service
@EnableConfigurationProperties(ScoringProperties::class)
class BoxMakerService(
    val scoringProperties: ScoringProperties
) : CoefficientMakerService {

    override fun calculateAge(age: Int): Double {
        return when (age) {
            in 1..45 -> (20 * 2.0.pow(-age / 45.0)).toNormalFormat()
            0 -> 0.0
            else -> 5.0
        }
    }

    override fun calculateWeight(weight: Double?, isMale: Boolean): Double {
        return weight?.let {
            when {
                isMale -> {
                    return when (it) {
                        in 1.0..80.0 -> (20 * 2.0.pow(-it / 40.0)).toNormalFormat()
                        0.0 -> 0.0
                        else -> 5.0
                    }
                }

                else -> {
                    return when (it) {
                        in 1.0..60.0 -> (20 * 2.0.pow(-it / 60.0)).toNormalFormat()
                        0.0 -> 0.0
                        else -> 5.0
                    }
                }
            }
        } ?: 0.0
    }

    override fun calculateHeight(height: Double?): Double {
        return height?.let {
            when (it) {
                in 0.1..2.0 -> (20 * 2.0.pow(-it)).toNormalFormat()
                0.0 -> 0.0
                else -> 5.0
            }
        } ?: 0.0
    }

    override fun calculateAverageForGames(games: List<SportsmenGamesResponse>): Double {
        if (games.isEmpty()) return 0.0

        val totalScore = games.sumOf { game ->
            val baseScore: Double = scoringProperties.places[game.place]?.toDouble() ?: 0.0
            val officialScore: Double = when {
                (game.isOfficial == true) -> scoringProperties.officialPlaces[game.place]?.toDouble() ?: 0.0
                else -> 0.0
            }
            baseScore + officialScore
        }.toNormalFormat()

        return totalScore / games.size
    }

    override fun calculateSanctions(sanctions: List<SportsmenSanctionsResponse>): Double {
        val cnt = sanctions.size.toDouble()
        return when {
            cnt > 0 -> (1.0 / 20.0 * cnt.pow(2.0)).toNormalFormat()
            else -> 0.0
        }
    }

    override fun calculateLastWins(games: List<SportsmenGamesResponse>): Double {
        if (games.isEmpty()) return 0.0
        return 2.0 * games.size
    }
}
