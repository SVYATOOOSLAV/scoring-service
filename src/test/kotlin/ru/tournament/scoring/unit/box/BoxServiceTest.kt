package ru.tournament.scoring.unit.box

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import ru.tournament.scoring.dto.SportsmenRequestDto
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.common.model.ScoreStepResult
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.exception.BusinessLogicException
import ru.tournament.scoring.logic.exception.Codes
import ru.tournament.scoring.logic.service.impl.box.BoxScoringStep
import ru.tournament.scoring.logic.service.impl.box.BoxService
import ru.tournament.scoring.logic.service.impl.box.steps.AgeCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.AverageWinsBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.HeightCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.SanctionsCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.WeightCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.WinnerCoefficientBoxStep
import ru.tournament.storage.dto.SportsmenInfoRequest
import ru.tournament.storage.dto.SportsmenInfoResponse
import ru.tournament.storage.dto.SportsmenRateRequest
import java.time.LocalDate
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class BoxServiceTest {

    private lateinit var boxService: BoxService

    @Mock
    lateinit var tournamentStorageService: TournamentStorageService

    @Mock
    lateinit var ageCoefficientBoxStep: AgeCoefficientBoxStep

    @Mock
    lateinit var averageWinsBoxStep: AverageWinsBoxStep

    @Mock
    lateinit var heightCoefficientBoxStep: HeightCoefficientBoxStep

    @Mock
    lateinit var sanctionsCoefficientBoxStep: SanctionsCoefficientBoxStep

    @Mock
    lateinit var weightCoefficientBoxStep: WeightCoefficientBoxStep

    @Mock
    lateinit var winnerCoefficientBoxStep: WinnerCoefficientBoxStep

    private lateinit var steps: List<BoxScoringStep>

    private var sportsmenRequest = SportsmenRequestDto(1, Sport.BOX.value, 12)
    private var sportsmenInfoRequest = SportsmenInfoRequest(sportsmenRequest.sportsmenId, sportsmenRequest.sport)

    private var sportsmenInfoResponse = SportsmenInfoResponse(
        sportsmenRequest.sportsmenId,
        sportsmenRequest.sport,
        true,
        LocalDate.of(2004, 12, 29),
        82.0,
        1.83
    )

    private var sportsmenInfo = SportsmenInfo(
        sportsmenInfoResponse.id,
        sportsmenInfoResponse.sport,
        sportsmenInfoResponse.isMale,
        sportsmenInfoResponse.birthday,
        sportsmenInfoResponse.weight,
        sportsmenInfoResponse.height,
        sportsmenRequest.period
    )

    private var possibleScore = 10.0

    @BeforeEach
    fun setUp() {
        steps = listOf(
            ageCoefficientBoxStep,
            averageWinsBoxStep,
            heightCoefficientBoxStep,
            sanctionsCoefficientBoxStep,
            weightCoefficientBoxStep,
            winnerCoefficientBoxStep,
        )

        boxService = BoxService(steps, tournamentStorageService)

        `when`(tournamentStorageService.getSportsmenInfo(sportsmenInfoRequest))
            .thenReturn(sportsmenInfoResponse)

        `when`(ageCoefficientBoxStep.calculate(sportsmenInfo))
            .thenReturn(ScoreStepResult(possibleScore, Step.AGE))

        `when`(averageWinsBoxStep.calculate(sportsmenInfo))
            .thenReturn(ScoreStepResult(possibleScore, Step.AVERAGE_WINS))

        `when`(heightCoefficientBoxStep.calculate(sportsmenInfo))
            .thenReturn(ScoreStepResult(possibleScore, Step.HEIGHT))

        `when`(sanctionsCoefficientBoxStep.calculate(sportsmenInfo))
            .thenReturn(ScoreStepResult(possibleScore, Step.SANCTIONS))

        `when`(weightCoefficientBoxStep.calculate(sportsmenInfo))
            .thenReturn(ScoreStepResult(possibleScore, Step.WEIGHT))

        `when`(winnerCoefficientBoxStep.calculate(sportsmenInfo))
            .thenReturn(ScoreStepResult(possibleScore, Step.WINNER))
    }

    @Test
    fun scoreSportsmenSuccess() {
        val expectedScore = 90.0
        val expectedResult = 0

        `when`(
            tournamentStorageService.updateRateSportsmen(
                SportsmenRateRequest(sportsmenRequest.sportsmenId, sportsmenRequest.sport, expectedScore)
            )
        ).thenReturn(Result(0))

        val result = boxService.score(sportsmenRequest)

        assertEquals(expectedResult, result.code)
    }

    @Test
    fun scoreSportsmenCorrectRateSuccess() {
        val expectedScore = 90.0

        val rateRequestCaptor = argumentCaptor<SportsmenRateRequest>()

        `when`(tournamentStorageService.updateRateSportsmen(any<SportsmenRateRequest>()))
            .thenReturn(Result(0))

        boxService.score(sportsmenRequest)

        verify(tournamentStorageService).updateRateSportsmen(rateRequestCaptor.capture())

        assertEquals(expectedScore, rateRequestCaptor.firstValue.rate)
    }

    @Test
    fun exceptionWhileUpdatingRateForSportsmenTest() {
        val expectedScore = 90.0
        val expectedResult = -100

        `when`(
            tournamentStorageService.updateRateSportsmen(
                SportsmenRateRequest(sportsmenRequest.sportsmenId, sportsmenRequest.sport, expectedScore)
            )
        ).thenReturn(Result(expectedResult))

        val e = assertThrows(BusinessLogicException::class.java) {
            boxService.score(sportsmenRequest)
        }

        assertEquals(Codes.PROCEDURE_ERROR.code, e.code)
        assertEquals(Codes.PROCEDURE_ERROR.message, e.message)
    }
}
