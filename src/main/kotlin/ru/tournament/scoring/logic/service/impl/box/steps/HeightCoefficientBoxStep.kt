package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.common.model.SportsmenResultScore
import ru.tournament.scoring.logic.service.ScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep

@Component
class HeightCoefficientBoxStep(
    private val boxMakerService: BoxMakerService
) : BoxScoringStep {

    override fun step(): Step = Step.HEIGHT

    override fun calculate(info: SportsmenInfo): SportsmenResultScore {
        val result = boxMakerService.calculateHeight(info.height)
        return SportsmenResultScore(
            resultScore = result,
            typeStep = step()
        )
    }
}