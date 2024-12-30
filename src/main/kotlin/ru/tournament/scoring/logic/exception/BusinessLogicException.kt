package ru.tournament.scoring.logic.exception

data class BusinessLogicException(val code: Int, override val message: String) : Exception(message){
    constructor(error: Codes) : this(error.code, error.message)

    constructor(error: Codes, message: String)
            : this(error.code, String.format(error.message, message))
}

