package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.ScoreStepResult
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep

@Component
class WeightCoefficientBoxStep(
    private val boxMakerService: BoxMakerService
) : BoxScoringStep {

    override fun step(): Step = Step.WEIGHT

    override fun calculate(info: SportsmenInfo): ScoreStepResult {
        val result = boxMakerService.calculateWeight(
            weight = info.weight,
            isMale = info.isMale
        )
        return ScoreStepResult(
            resultScore = result,
            typeStep = step()
        )
    }
}
