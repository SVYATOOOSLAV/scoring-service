package ru.tournament.scoring.logic.exception

enum class Codes(val code: Int, val message: String) {
    VALIDATION_ERROR(-100, "Ошибка валидации: %s"),
    PROCEDURE_ERROR(-99, "Ошибка выполнения процедуры")
}
