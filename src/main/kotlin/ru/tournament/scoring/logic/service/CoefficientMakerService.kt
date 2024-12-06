package ru.tournament.scoring.logic.service

import ru.tournament.model.SportsmenGamesResponse
import ru.tournament.model.SportsmenSanctionsResponse

interface CoefficientMakerService {
    fun calculateAge(age: Int): Double
    fun calculateWeight(weight: Double?, isMale: Boolean): Double
    fun calculateHeight(height: Double?): Double
    fun calculateAverageForGames(games: List<SportsmenGamesResponse>): Double
    fun calculateSanctions(sanctions: List<SportsmenSanctionsResponse>): Double
    fun calculateLastWins(games: List<SportsmenGamesResponse>): Double
}
