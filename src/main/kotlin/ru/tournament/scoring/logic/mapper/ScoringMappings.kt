package ru.tournament.scoring.logic.mapper

import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.storage.dto.SportsmenInfoResponse
import java.util.*

fun SportsmenInfoResponse.toSportsmenInfo(periodValidation: Int?): SportsmenInfo {
    return SportsmenInfo(
        id = this.id,
        sport = this.sport,
        isMale = this.isMale,
        birthday = this.birthday,
        weight = this.weight,
        height = this.height,
        periodValidation = periodValidation
    )
}

fun Double.toNormalFormat(): Double {
    return "%.3f".format(Locale.US, this).toDouble()
}
