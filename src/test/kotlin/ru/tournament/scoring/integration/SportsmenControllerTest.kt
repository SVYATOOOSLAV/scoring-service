package ru.tournament.scoring.integration

import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.tournament.scoring.AbstractMockMvc
import ru.tournament.scoring.dto.SportsmenRequestDto
import ru.tournament.scoring.dto.SportsmenSanction
import ru.tournament.scoring.logic.exception.Codes
import ru.tournament.storage.dto.SportsmenGamesResponse
import ru.tournament.storage.dto.SportsmenInfoResponse
import ru.tournament.storage.dto.SportsmenRateResponse
import testFixtures.stubs.getSportsmenGamesLastPeriodStub
import testFixtures.stubs.getSportsmenGamesStub
import testFixtures.stubs.getSportsmenInfoStub
import testFixtures.stubs.getSportsmenSanctionsStub
import testFixtures.stubs.updateSportsmenRateStub
import java.time.LocalDate

class SportsmenControllerTest : AbstractMockMvc() {

    val request = SportsmenRequestDto(
        sportsmenId = 1,
        sport = "box",
        period = 12
    )

    var infoResponse = SportsmenInfoResponse(
        id = request.sportsmenId,
        sport = request.sport,
        isMale = true,
        birthday = LocalDate.of(1970, 1, 1),
        weight = 88.7,
        height = 1.87
    )

    val allGames = listOf(
        SportsmenGamesResponse(
            sportsmenId = request.sportsmenId,
            tournamentId = 1,
            sport = request.sport,
            isOfficial = true,
            place = 2
        )
    )

    val sanctions = listOf(
        SportsmenSanction(
            sportsmenId = request.sportsmenId,
            sanctionId = 1
        )
    )

    val gamesLastPeriod = listOf(
        SportsmenGamesResponse(
            sportsmenId = request.sportsmenId,
            tournamentId = 1,
            sport = request.sport,
            isOfficial = true,
            place = 2
        )
    )

    val response = SportsmenRateResponse(code = 0)

    @Test
    fun positiveScoreSportsmenTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").doesNotExist())
    }

    @Test
    fun getSportsmenInfoReturnInternalErrorTest() {
        getSportsmenInfoStub(500, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError)
            .andExpect(jsonPath("$.code").value(Codes.EXTERNAL_SYSTEM_ERROR.code))
            .andExpect(jsonPath("$.message").value(Codes.EXTERNAL_SYSTEM_ERROR.message))
    }

    @Test
    fun getSportsmenInfoBadRequestTest() {
        getSportsmenInfoStub(400, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError)
            .andExpect(jsonPath("$.code").value(Codes.EXTERNAL_SYSTEM_ERROR.code))
            .andExpect(jsonPath("$.message").value(Codes.EXTERNAL_SYSTEM_ERROR.message))
    }

    @Test
    fun getSportsmenGamesReturnInternalErrorTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(500, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").doesNotExist())
    }

    @Test
    fun getSportsmenGamesReturnBadRequestTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(400, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").doesNotExist())
    }

    @Test
    fun getSportsmenGamesLastPeriodReturnInternalErrorTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(500, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").doesNotExist())
    }

    @Test
    fun getSportsmenGamesLastPeriodReturnIBadRequestTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(400, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").doesNotExist())
    }

    @Test
    fun getSportsmenSanctionsReturnInternalErrorTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(500, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").doesNotExist())
    }

    @Test
    fun getSportsmenSanctionsReturnBadRequestTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(400, sanctions)
        updateSportsmenRateStub(200, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").doesNotExist())
    }

    @Test
    fun updateSportsmenRateReturnErrorTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(500, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError)
            .andExpect(jsonPath("$.code").value(Codes.EXTERNAL_SYSTEM_ERROR.code))
            .andExpect(jsonPath("$.message").value(Codes.EXTERNAL_SYSTEM_ERROR.message))
    }

    @Test
    fun updateSportsmenRateReturnEBadRequestTest() {
        getSportsmenInfoStub(200, infoResponse)
        getSportsmenGamesStub(200, allGames)
        getSportsmenGamesLastPeriodStub(200, gamesLastPeriod)
        getSportsmenSanctionsStub(200, sanctions)
        updateSportsmenRateStub(400, response)

        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(request))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError)
            .andExpect(jsonPath("$.code").value(Codes.EXTERNAL_SYSTEM_ERROR.code))
            .andExpect(jsonPath("$.message").value(Codes.EXTERNAL_SYSTEM_ERROR.message))
    }
}
