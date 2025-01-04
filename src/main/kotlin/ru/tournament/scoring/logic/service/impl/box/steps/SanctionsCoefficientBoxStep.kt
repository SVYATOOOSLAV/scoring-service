package ru.tournament.scoring.logic.service.impl.box.steps

import org.springframework.stereotype.Component
import ru.tournament.scoring.BASE_PERIOD
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.ScoreStepResult
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep
import ru.tournament.storage.dto.SportsmenSanctionsRequest

@Component
class SanctionsCoefficientBoxStep(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : BoxScoringStep {

    override fun step(): Step = Step.SANCTIONS

    override fun calculate(info: SportsmenInfo): ScoreStepResult {
        val sanctionsRequest = SportsmenSanctionsRequest(
            id = info.id,
            sport = info.sport,
            period = info.periodValidation ?: BASE_PERIOD
        )
        val sanctionsList = tournamentStorageService.getSportsmenSanction(sanctionsRequest)
        val result = boxMakerService.calculateSanctions(sanctionsList)
        return ScoreStepResult(
            resultScore = result,
            typeStep = step()
        )
    }
}
