package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.common.model.SportsmenResultScore
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep
import java.time.LocalDate
import java.time.Period

@Component
class AgeCoefficientBoxStep(
    private val boxMakerService: BoxMakerService
) : BoxScoringStep {

    override fun step(): Step = Step.AGE

    override fun calculate(info: SportsmenInfo): SportsmenResultScore {
        val age = Period.between(info.birthday, LocalDate.now()).years
        val result = boxMakerService.calculateAge(age)
        return SportsmenResultScore(
            resultScore = result,
            typeStep = step()
        )
    }
}
