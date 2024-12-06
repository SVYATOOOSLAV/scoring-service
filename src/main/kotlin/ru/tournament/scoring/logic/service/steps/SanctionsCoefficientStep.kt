package ru.tournament.scoring.logic.service.steps

import org.springframework.stereotype.Component
import ru.tournament.model.SportsmenSanctionsRequest
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.ScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService

@Component
class SanctionsCoefficientStep(
    private val boxMakerService: BoxMakerService,
    private val tournamentStorageService: TournamentStorageService
) : ScoringStep {

    override fun calculate(info: SportsmenInfo): Double {
        val sanctionsRequest = SportsmenSanctionsRequest(
            info.id,
            info.sport,
            info.periodValidation
        )
        val sanctionsList = tournamentStorageService.getSportsmenSanction(sanctionsRequest)
        return if (sanctionsList.isNotEmpty()) boxMakerService.calculateSanctions(sanctionsList) else 1.0
    }
}
