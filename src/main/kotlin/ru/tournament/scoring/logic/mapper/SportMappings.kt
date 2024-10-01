package ru.tournament.scoring.logic.mapper

import ru.tournament.scoring.logic.common.enums.Sport

fun String.toEnumSport(): Sport {
    return Sport.entries
        .find {
            it.value == this
        } ?: throw IllegalArgumentException("Sport value [$this] is invalid")
}