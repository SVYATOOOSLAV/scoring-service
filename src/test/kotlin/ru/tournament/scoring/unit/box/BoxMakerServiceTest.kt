package ru.tournament.scoring.unit.box

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ru.tournament.scoring.configuration.properties.ScoringProperties
import ru.tournament.scoring.generateSportsmenGames
import ru.tournament.scoring.generateSportsmenSanctions
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.service.impl.box.BoxMakerService
import ru.tournament.storage.dto.SportsmenGamesResponse
import ru.tournament.storage.dto.SportsmenSanctionsResponse
import java.util.stream.Stream

@ExtendWith(MockitoExtension::class)
class BoxMakerServiceTest {

    @InjectMocks
    private lateinit var service: BoxMakerService

    @Mock
    private lateinit var scoringProperties: ScoringProperties

    @BeforeEach
    fun setUp() {
        Mockito.lenient().`when`(scoringProperties.places).thenReturn(
            mapOf(
                1 to 10,
                2 to 9,
                3 to 8,
                4 to 7,
                5 to 6,
                6 to 5,
                7 to 4,
                8 to 3,
                9 to 2,
                10 to 1
            )
        )

        Mockito.lenient().`when`(scoringProperties.officialPlaces).thenReturn(
            mapOf(
                1 to 5,
                2 to 4,
                3 to 3,
                4 to 2,
                5 to 1,
                7 to 1,
                8 to 1,
                9 to 1,
                10 to 1
            )
        )
    }

    @ParameterizedTest
    @MethodSource("provideDataForCalculateScoreByAge")
    fun calculateAgeTest(age: Int, expectedScore: Double) {
        val result = service.calculateAge(age)
        assertEquals(expectedScore, result)
    }

    @ParameterizedTest
    @MethodSource("provideDataForCalculateScoreByWeight")
    fun calculateWeightTest(weight: Double?, isMale: Boolean, expectedScore: Double) {
        val result = service.calculateWeight(weight, isMale)
        assertEquals(expectedScore, result)
    }

    @ParameterizedTest
    @MethodSource("provideDataForCalculateScoreByHeight")
    fun calculateHeightTest(height: Double?, expectedScore: Double) {
        val result = service.calculateHeight(height)
        assertEquals(expectedScore, result)
    }

    @ParameterizedTest
    @MethodSource("provideDataForCalculateScoreByAverageForGames")
    fun calculateAverageForGamesTest(games: List<SportsmenGamesResponse>, expectedScore: Double) {
        val result = service.calculateAverageForGames(games)
        assertEquals(expectedScore, result)
    }

    @ParameterizedTest
    @MethodSource("provideDataForCalculateScoreBySanctions")
    fun calculateSanctionsTest(sanctions: List<SportsmenSanctionsResponse>, expectedScore: Double) {
        val result = service.calculateSanctions(sanctions)
        assertEquals(expectedScore, result)
    }

    @ParameterizedTest
    @MethodSource("provideDataForCalculateScoreByLastWins")
    fun calculateLastWinsTest(wins: List<SportsmenGamesResponse>, expectedScore: Double) {
        val result = service.calculateLastWins(wins)
        assertEquals(expectedScore, result)
    }

    companion object {
        @JvmStatic
        fun provideDataForCalculateScoreByAge(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(18, 15.157),
                Arguments.of(1, 19.694),
                Arguments.of(45, 10),
                Arguments.of(0, 0),
                Arguments.of(50, 5.0),
            )
        }

        @JvmStatic
        fun provideDataForCalculateScoreByWeight(): Stream<Arguments> {
            return Stream.of(
                // male
                Arguments.of(null, true, 0.0),
                Arguments.of(0.0, true, 0.0),
                Arguments.of(1.0, true, 19.656),
                Arguments.of(60.0, true, 7.071),
                Arguments.of(80.0, true, 5.0),
                Arguments.of(85.0, true, 5.0),
                // female
                Arguments.of(null, false, 0.0),
                Arguments.of(0.0, false, 0.0),
                Arguments.of(1.0, false, 19.77),
                Arguments.of(45.0, false, 11.892),
                Arguments.of(60.0, false, 10.0),
                Arguments.of(65.0, false, 5.0),
            )
        }

        @JvmStatic
        fun provideDataForCalculateScoreByHeight(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(null, 0.0),
                Arguments.of(0.0, 0.0),
                Arguments.of(0.1, 18.661),
                Arguments.of(1.8, 5.743),
                Arguments.of(2.0, 5.0),
                Arguments.of(2.5, 5.0),
            )
        }

        @JvmStatic
        fun provideDataForCalculateScoreByAverageForGames(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(emptyList<SportsmenGamesResponse>(), 0.0),
                Arguments.of(generateSportsmenGames(2, Sport.BOX.value), 12),
                Arguments.of(generateSportsmenGames(5, Sport.BOX.value), 9.8),
                Arguments.of(generateSportsmenGames(8, Sport.BOX.value), 7.75),
                Arguments.of(generateSportsmenGames(15, Sport.BOX.value), 4.4),
                Arguments.of(generateSportsmenGames(20, Sport.BOX.value), 5.6),
                Arguments.of(generateSportsmenGames(30, Sport.BOX.value), 4.3),
                Arguments.of(generateSportsmenGames(40, Sport.BOX.value), 4.875),
                Arguments.of(generateSportsmenGames(50, Sport.BOX.value), 4.82),
            )
        }

        @JvmStatic
        fun provideDataForCalculateScoreBySanctions(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(emptyList<SportsmenSanctionsResponse>(), 0.0),
                Arguments.of(generateSportsmenSanctions(1), 0.05),
                Arguments.of(generateSportsmenSanctions(2), 0.2),
                Arguments.of(generateSportsmenSanctions(3), 0.45),
                Arguments.of(generateSportsmenSanctions(10), 5.0),
                Arguments.of(generateSportsmenSanctions(20), 20.0),
            )
        }

        @JvmStatic
        fun provideDataForCalculateScoreByLastWins(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(emptyList<SportsmenGamesResponse>(), 0.0),
                Arguments.of(generateSportsmenGames(8, Sport.BOX.value), 16.0),
                Arguments.of(generateSportsmenGames(10, Sport.BOX.value), 20.0),
                Arguments.of(generateSportsmenGames(20, Sport.BOX.value), 40.0),
                Arguments.of(generateSportsmenGames(25, Sport.BOX.value), 50.0),
            )
        }
    }
}
