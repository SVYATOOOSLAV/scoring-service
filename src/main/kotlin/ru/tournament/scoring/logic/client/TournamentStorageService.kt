package ru.tournament.scoring.logic.client

import org.springframework.stereotype.Service
import ru.tournament.client.TournamentStorageApiClient
import ru.tournament.model.*
import ru.tournament.scoring.logic.common.model.Result

@Service
class TournamentStorageService(
    private val client: TournamentStorageApiClient
) {

    fun getSportsmenInfo(infoRequest: SportsmenInfoRequest): SportsmenInfoResponse {
        return client.getSportsmenInfo(infoRequest).block()
            ?: throw Exception("SportsmenInfoResponse not found")
    }

    fun getSportsmenGames(gamesRequest: SportsmenGamesRequest): List<SportsmenGamesResponse> {
        return client.getSportsmenGames(gamesRequest).block().orEmpty()
    }

    fun getSportsmenSanction(sanctionsRequest: SportsmenSanctionsRequest): List<SportsmenSanctionsResponse> {
        return client.getSportsmenSanctions(sanctionsRequest).block().orEmpty()
    }

    fun updateRateSportsmen(sportsmenRateRequest: SportsmenRateRequest): Result {
        val response = client.updateSportsmenRate(sportsmenRateRequest).block()
            ?: throw Exception("SportsmenResponseDto not found")

        return Result(response.code, response.message)
    }
}
