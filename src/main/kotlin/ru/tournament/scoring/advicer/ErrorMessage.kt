package ru.tournament.scoring.advicer

import ru.tournament.scoring.logic.exception.Codes

data class ErrorMessage(val code: Int, val message: String) {
    constructor(error: Codes) : this(error.code, error.message)
}
