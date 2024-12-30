package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.common.model.SportsmenResultScore
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep

@Component
class WeightCoefficientBoxStep(
    private val boxMakerService: BoxMakerService
) : BoxScoringStep {

    override fun step(): Step = Step.WEIGHT

    override fun calculate(info: SportsmenInfo): SportsmenResultScore {
        val result = boxMakerService.calculateWeight(
            weight = info.weight,
            isMale = info.isMale
        )
        return SportsmenResultScore(
            resultScore = result,
            typeStep = step()
        )
    }
}