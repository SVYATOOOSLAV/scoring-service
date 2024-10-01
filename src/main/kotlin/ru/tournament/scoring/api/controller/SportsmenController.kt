package ru.tournament.scoring.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.tournament.scoring.logic.common.dto.SportsmenRequest
import ru.tournament.scoring.logic.common.dto.SportsmenResponse
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.mapper.toCorrect
import ru.tournament.scoring.logic.service.SelectorService
import java.time.LocalDate

@RestController
class SportsmenController(
    private val selectorService: SelectorService
) {

    @GetMapping("/sportsmen/{id}")
    fun getSportsmen(@PathVariable id: Int): ResponseEntity<SportsmenResponse> {
        return ResponseEntity.ok(
            SportsmenResponse(
                name = "stubName",
                surname = "stubSurname",
                birthday = LocalDate.now(),
                sport = Sport.BOX.value
            )
        )
    }

    @PostMapping("/sportsmen")
    fun postSportsmen(@RequestBody sportsmen: SportsmenRequest): ResponseEntity<SportsmenResponse> {
        selectorService.selectScoringBySport(sportsmen.toCorrect())
        return ResponseEntity.ok(
            SportsmenResponse(
                name = sportsmen.name,
                surname = sportsmen.surname,
                birthday = sportsmen.birthday,
                sport = sportsmen.sport
            )
        )
    }
}
