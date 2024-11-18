package ru.tournament.scoring.logic.client

import org.springframework.stereotype.Service
import ru.tournament.client.TournamentStorageApiClient
import ru.tournament.model.*
import ru.tournament.scoring.logic.common.model.Result

@Service
class TournamentStorageService(
    private val client: TournamentStorageApiClient
) {

    fun getSportsmenInfo(idSportsmen: Int, sport: String): SportsmenInfoResponse {
        return client.getSportsmenInfo(idSportsmen, sport).block()
            ?: throw Exception("SportsmenInfoResponse not found")
    }

    fun getAllSportsmenGames(idSportsmen: Int, sport: String): List<SportsmenGame> {
        return client.getAllSportsmenGames(idSportsmen, sport).block().orEmpty()
    }

    fun getSportsmenSanctionLastPeriod(idSportsmen: Int, sport: String, period: Int): List<SportsmenSanction> {
        return client.getSanctionLastYear(idSportsmen, sport, period).block().orEmpty()
    }

    fun getSportsmenGamesLastPeriod(idSportsmen: Int, sport: String, period: Int): List<SportsmenGame> {
        return client.getGamesLastPeriod(idSportsmen, sport, period).block().orEmpty()
    }

    fun updateRateSportsmen(idSportsmen: Int, sport: String, rate: Double): Result {
        val response = client.updateSportsmenRate(idSportsmen, sport, rate).block()
            ?: throw Exception("SportsmenResponseDto not found")

        return Result(response.code, response.message)
    }
}
