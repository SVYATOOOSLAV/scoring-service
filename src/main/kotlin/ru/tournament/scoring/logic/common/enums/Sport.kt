package ru.tournament.scoring.logic.common.enums

import ru.tournament.scoring.logic.exception.Codes
import ru.tournament.scoring.logic.exception.ValidationErrorException
import java.lang.IllegalArgumentException

enum class Sport(val value: String) {
    BEACH_VOLLEYBALL("beach_volleyball"),
    BOX("box");

    companion object {
        fun toEnum(value: String): Sport {
            try {
                return Sport.valueOf(value.uppercase())
            } catch (e: IllegalArgumentException) {
                throw ValidationErrorException(Codes.VALIDATION_ERROR, "Sport [$value] not found")
            }
        }
    }
}
