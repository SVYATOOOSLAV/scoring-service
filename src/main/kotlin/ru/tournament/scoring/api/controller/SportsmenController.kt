package ru.tournament.scoring.api.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.tournament.scoring.api.ScoringServiceApi
import ru.tournament.scoring.dto.SportsmenRequestDto
import ru.tournament.scoring.dto.SportsmenResponseDto
import ru.tournament.scoring.logic.service.SelectorService

private val logger = KotlinLogging.logger {}

@RestController
class SportsmenController(
    private val service: SelectorService
) : ScoringServiceApi {

    override fun scoreSportsmen(
        sportsmenRequestDto: SportsmenRequestDto
    ): ResponseEntity<SportsmenResponseDto> {
        val result = service.score(sportsmenRequestDto)

        logger.info { result }

        return ResponseEntity.ok().body(
            SportsmenResponseDto(
                result.code,
                result.message
            )
        )
    }
}
