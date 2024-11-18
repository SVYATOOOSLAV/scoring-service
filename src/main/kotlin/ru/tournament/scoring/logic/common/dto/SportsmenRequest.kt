package ru.tournament.scoring.logic.common.dto

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class SportsmenRequest(
    @JsonAlias(value = ["name", "name_sportsmen"])
    val name: String,

    @JsonProperty("surname")
    val surname: String,

    @JsonAlias(value = ["birth_date", "birthday"])
    val birthday: LocalDate,

    @JsonProperty("sport")
    val sport: String,

    @JsonProperty("rate")
    val rate: Double,

    @JsonProperty("weight")
    val weight: Double,

    @JsonProperty("is_male")
    val isMale: Boolean,

    @JsonProperty("height")
    val height: Double,
)
