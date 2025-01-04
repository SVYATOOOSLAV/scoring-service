package ru.tournament.scoring

import ru.tournament.storage.dto.SportsmenGamesResponse
import ru.tournament.storage.dto.SportsmenSanctionsResponse

fun generateSportsmenGames(cnt: Int, sport: String): List<SportsmenGamesResponse> {
    return (1..cnt).mapIndexed { ind, el ->
        SportsmenGamesResponse(
            sportsmenId = 1,
            tournamentId = el,
            sport = sport,
            isOfficial = ind % 2 == 0,
            place = ind % 15 + 1
        )
    }
}

fun generateSportsmenSanctions(cnt: Int): List<SportsmenSanctionsResponse> {
    return (1..cnt).map {
        SportsmenSanctionsResponse(1, it)
    }
}
