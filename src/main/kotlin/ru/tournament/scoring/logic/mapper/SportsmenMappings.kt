package ru.tournament.scoring.logic.mapper

import ru.tournament.scoring.logic.common.dto.SportsmenRequest
import ru.tournament.scoring.logic.common.model.Sportsmen

fun SportsmenRequest.toCorrect(): Sportsmen {
    return Sportsmen(
        name = name,
        surname = surname,
        birthday = birthday,
        sport = sport.toEnumSport(),
        rate = rate
    )
}
