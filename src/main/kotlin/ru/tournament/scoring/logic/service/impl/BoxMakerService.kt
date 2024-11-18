package ru.tournament.scoring.logic.service.impl

import org.springframework.stereotype.Service
import ru.tournament.model.SportsmenGame
import ru.tournament.model.SportsmenSanction
import ru.tournament.scoring.internal.configuration.properties.getPlaceMap
import ru.tournament.scoring.internal.configuration.properties.getPlaceMapForOfficial
import ru.tournament.scoring.logic.service.CoefficientMakerService
import kotlin.math.pow

@Service
class BoxMakerService : CoefficientMakerService {
    override fun calculateAge(age: Int): Double {
        return when {
            age in 1..45 -> -age * age / 2300.0 + 1
            else -> 0.12
        }
    }

    override fun calculateWeight(weight: Double?, isMale: Boolean): Double {
        return weight?.let {
            when {
                isMale -> {
                    return when {
                        it in 1.0..80.0 -> -it * it / 8000.0 + 1
                        else -> 0.2
                    }
                }
                else -> {
                    return when {
                        it in 1.0..60.0 -> -it * it / 6000.0 + 1
                        else -> 0.4
                    }
                }
            }
        } ?: when (isMale) {
            true -> 0.2
            false -> 0.4
        }
    }

    override fun calculateHeight(height: Double?): Double {
        return height?.let {
            when {
                it in 0.1..2.0 -> -it * it / 5 + 1
                else -> 0.2
            }
        } ?: 0.2
    }

    override fun calculateAverageForGames(games: List<SportsmenGame>): Int {
        val placeMap = getPlaceMap()
        val placeOfficialMap = getPlaceMapForOfficial()

        val totalScore = games.sumOf { game ->
            val baseScore = placeMap[game.place] ?: 0
            val officialScore = if (game.isOfficial == true) placeOfficialMap[game.place] ?: 0 else 0
            baseScore + officialScore
        }

        return totalScore / games.size
    }

    override fun calculateSanctions(sanctions: List<SportsmenSanction>): Double {
        val cnt = sanctions.size
        return when {
            cnt in 0..10 -> -cnt * cnt / 130.0 + 1
            else -> 0.23
        }
    }

    override fun calculateLastWins(games: List<SportsmenGame>): Double {
        return (games.size + 7.0).pow(2) / 290
    }
}
