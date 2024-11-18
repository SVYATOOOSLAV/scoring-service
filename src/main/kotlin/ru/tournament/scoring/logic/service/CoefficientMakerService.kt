package ru.tournament.scoring.logic.service

import ru.tournament.model.SportsmenGame
import ru.tournament.model.SportsmenSanction

interface CoefficientMakerService {
    fun calculateAge(age: Int): Double
    fun calculateWeight(weight: Double?, isMale: Boolean): Double
    fun calculateHeight(height: Double?): Double
    fun calculateAverageForGames(games: List<SportsmenGame>): Int
    fun calculateSanctions(sanctions: List<SportsmenSanction>): Double
    fun calculateLastWins(games: List<SportsmenGame>): Double
}
