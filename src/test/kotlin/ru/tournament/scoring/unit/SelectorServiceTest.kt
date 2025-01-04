package ru.tournament.scoring.unit

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import ru.tournament.scoring.dto.SportsmenRequestDto
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.common.model.Result
import ru.tournament.scoring.logic.exception.Codes
import ru.tournament.scoring.logic.exception.ValidationErrorException
import ru.tournament.scoring.logic.service.ScoringService
import ru.tournament.scoring.logic.service.SelectorService
import ru.tournament.scoring.logic.service.impl.SelectorServiceImpl
import ru.tournament.scoring.logic.service.impl.box.BoxService

@ExtendWith(MockitoExtension::class)
class SelectorServiceTest {

    lateinit var service: SelectorService

    @Mock
    lateinit var sportMap: Map<Sport, ScoringService>

    @Mock
    lateinit var boxService: BoxService

    private var sportsmen = SportsmenRequestDto(1, "box", 12)

    @BeforeEach
    fun setUp() {
        sportMap = mapOf(
            Sport.BOX to boxService,
        )

        service = SelectorServiceImpl(sportMap)

        Mockito.lenient().`when`(boxService.score(sportsmen))
            .thenReturn(Result(0))
    }

    @Test
    fun selectorServiceSuccessTest() {
        val expectedCode = 0
        val result = service.score(sportsmen)

        assertEquals(expectedCode, result.code)
    }

    @Test
    fun selectorServiceUnknownSportTest() {
        sportsmen = SportsmenRequestDto(1, "unknown", 12)
        val expectedMessage = "Ошибка валидации: Sport [${sportsmen.sport}] not found"

        val e = assertThrows(ValidationErrorException::class.java) { service.score(sportsmen) }

        assertEquals(Codes.VALIDATION_ERROR.code, e.code)
        assertEquals(expectedMessage, e.message)
    }
}
