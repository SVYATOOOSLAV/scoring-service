package ru.tournament.scoring.logic.exception

data class ValidationErrorException(val code: Int, override val message: String) : RuntimeException(message) {
    constructor(error: Codes) : this(error.code, error.message)

    constructor(error: Codes, message: String) :
        this(error.code, String.format(error.message, message))
}
