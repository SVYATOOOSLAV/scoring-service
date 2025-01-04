package ru.tournament.scoring.unit.box

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ru.tournament.scoring.logic.client.TournamentStorageService
import ru.tournament.scoring.logic.common.enums.Step
import ru.tournament.scoring.logic.common.model.ScoreStepResult
import ru.tournament.scoring.logic.common.model.SportsmenInfo
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.scoring.logic.service.impl.box.steps.AgeCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.AverageWinsBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.HeightCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.SanctionsCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.WeightCoefficientBoxStep
import ru.tournament.scoring.logic.service.impl.box.steps.WinnerCoefficientBoxStep
import ru.tournament.storage.dto.SportsmenGamesRequest
import ru.tournament.storage.dto.SportsmenGamesResponse
import ru.tournament.storage.dto.SportsmenSanctionsRequest
import ru.tournament.storage.dto.SportsmenSanctionsResponse
import java.time.LocalDate
import java.time.Period

@ExtendWith(MockitoExtension::class)
class BoxStepTest {

    @InjectMocks
    private lateinit var ageStep: AgeCoefficientBoxStep

    @InjectMocks
    private lateinit var averageWinsStep: AverageWinsBoxStep

    @InjectMocks
    private lateinit var heightStep: HeightCoefficientBoxStep

    @InjectMocks
    private lateinit var sanctionsStep: SanctionsCoefficientBoxStep

    @InjectMocks
    private lateinit var weightStep: WeightCoefficientBoxStep

    @InjectMocks
    private lateinit var winnerStep: WinnerCoefficientBoxStep

    @Mock
    lateinit var boxMakerService: BoxMakerService

    @Mock
    lateinit var tournamentStorageService: TournamentStorageService

    private val info = SportsmenInfo(
        id = 1,
        sport = "box",
        isMale = true,
        birthday = LocalDate.of(2000, 1, 1),
        weight = 80.0,
        height = 1.8,
        periodValidation = 12
    )

    private var gamesRequest = SportsmenGamesRequest(info.id, info.sport, info.periodValidation)

    private val gamesList = listOf(
        SportsmenGamesResponse(info.id, 1, info.sport, true, 2),
        SportsmenGamesResponse(info.id, 2, info.sport, true, 3),
    )

    private val possibleScore = 20.0

    @Test
    fun calculateAgeCoefficientTest() {
        Mockito.lenient().`when`(
            boxMakerService.calculateAge(
                Period.between(info.birthday, LocalDate.now()).years
            )
        ).thenReturn(possibleScore)

        val expectedValue = ScoreStepResult(possibleScore, Step.AGE)
        val result = ageStep.calculate(info)

        assertEquals(expectedValue.resultScore, result.resultScore)
        assertEquals(expectedValue.typeStep, result.typeStep)
    }

    @Test
    fun calculateAverageWinsCoefficientTest() {
        gamesRequest = SportsmenGamesRequest(info.id, info.sport)

        Mockito.`when`(
            tournamentStorageService.getSportsmenGames(gamesRequest)
        ).thenReturn(gamesList)

        Mockito.`when`(
            boxMakerService.calculateAverageForGames(gamesList)
        ).thenReturn(possibleScore)

        val expectedValue = ScoreStepResult(possibleScore, Step.AVERAGE_WINS)
        val result = averageWinsStep.calculate(info)

        assertEquals(expectedValue.resultScore, result.resultScore)
        assertEquals(expectedValue.typeStep, result.typeStep)
    }

    @Test
    fun calculateHeightCoefficientTest() {
        Mockito.`when`(boxMakerService.calculateHeight(info.height))
            .thenReturn(possibleScore)

        val expectedValue = ScoreStepResult(possibleScore, Step.HEIGHT)
        val result = heightStep.calculate(info)

        assertEquals(expectedValue.resultScore, result.resultScore)
        assertEquals(expectedValue.typeStep, result.typeStep)
    }

    @Test
    fun calculateSanctionsCoefficientTest() {
        val sanctionsRequest = SportsmenSanctionsRequest(
            info.id,
            info.sport,
            info.periodValidation
        )
        val sanctionList = listOf(
            SportsmenSanctionsResponse(info.id, 1)
        )

        Mockito.`when`(
            tournamentStorageService.getSportsmenSanction(sanctionsRequest)
        ).thenReturn(sanctionList)

        Mockito.`when`(boxMakerService.calculateSanctions(sanctionList))
            .thenReturn(possibleScore)

        val expectedValue = ScoreStepResult(possibleScore, Step.SANCTIONS)
        val result = sanctionsStep.calculate(info)

        assertEquals(expectedValue.resultScore, result.resultScore)
        assertEquals(expectedValue.typeStep, result.typeStep)
    }

    @Test
    fun calculateWeightCoefficientTest() {
        Mockito.`when`(boxMakerService.calculateWeight(info.weight, info.isMale)).thenReturn(possibleScore)

        val expectedValue = ScoreStepResult(possibleScore, Step.WEIGHT)
        val result = weightStep.calculate(info)

        assertEquals(expectedValue.resultScore, result.resultScore)
        assertEquals(expectedValue.typeStep, result.typeStep)
    }

    @Test
    fun calculateWinnerCoefficientTest() {
        Mockito.`when`(tournamentStorageService.getSportsmenGames(gamesRequest)).thenReturn(gamesList)
        Mockito.`when`(boxMakerService.calculateLastWins(gamesList)).thenReturn(possibleScore)

        val expectedValue = ScoreStepResult(possibleScore, Step.WINNER)
        val result = winnerStep.calculate(info)

        assertEquals(expectedValue.resultScore, result.resultScore)
        assertEquals(expectedValue.typeStep, result.typeStep)
    }
}
