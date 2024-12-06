package ru.tournament.scoring.logic.mapper

import ru.tournament.model.SportsmenInfoResponse
import ru.tournament.scoring.logic.common.model.SportsmenInfo


fun SportsmenInfoResponse.toSportsmenInfo(periodValidation: Int): SportsmenInfo{
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
