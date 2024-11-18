package ru.tournament.scoring.logic.common.model

data class Result(
    val code: Int,
    val message: String
) {
    fun isSuccess() = code == 0
}
