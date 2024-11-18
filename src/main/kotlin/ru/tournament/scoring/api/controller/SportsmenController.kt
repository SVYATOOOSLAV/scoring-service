package ru.tournament.scoring.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.tournament.api.ScoringServiceApi
import ru.tournament.model.SportsmenRequestDto
import ru.tournament.model.SportsmenResponseDto
import ru.tournament.scoring.logic.service.SelectorService

@RestController
class SportsmenController(
    private val service: SelectorService
) : ScoringServiceApi {

    override fun scoreSportsmen(
        sportsmenRequestDto: SportsmenRequestDto
    ): ResponseEntity<SportsmenResponseDto> {
        val result = service.score(sportsmenRequestDto)

        return ResponseEntity.ok().body(
            SportsmenResponseDto(
                result.code,
                result.message
            )
        )
    }
}
