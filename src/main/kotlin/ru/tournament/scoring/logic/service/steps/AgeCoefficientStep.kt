package ru.tournament.scoring.logic.service.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.ScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import java.time.LocalDate
import java.time.Period

@Component
class AgeCoefficientStep(
    private val boxMakerService: BoxMakerService
) : ScoringStep {

    override fun calculate(info: SportsmenInfo): Double {
        val age = Period.between(info.birthday, LocalDate.now()).years
        return boxMakerService.calculateAge(age)
    }
}
