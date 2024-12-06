package ru.tournament.scoring.logic.service.steps

import org.springframework.stereotype.Component
import ru.tournament.model.SportsmenInfoResponse
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.ScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService

@Component
class HeightCoefficientStep(
    private val boxMakerService: BoxMakerService
) : ScoringStep {

    override fun calculate(info: SportsmenInfo): Double {
        return boxMakerService.calculateHeight(info.height)
    }
}