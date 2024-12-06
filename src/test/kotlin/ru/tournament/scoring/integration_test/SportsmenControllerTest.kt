package ru.tournament.scoring.integration_test

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.tournament.model.*
import ru.tournament.scoring.AbstractMockMvc
import java.time.LocalDate

class SportsmenControllerTest : AbstractMockMvc() {

    @Test
    fun positiveScoreSportsmenTest() {
        val requestDto = SportsmenRequestDto(
            sportsmenId = 1,
            sport = "box",
            period = 12
        )

        val requestGames = SportsmenGamesRequest(
            requestDto.sportsmenId,
            requestDto.sport
        )

        val sportsmenResponse = SportsmenInfoResponse(
            id = requestDto.sportsmenId,
            sport = requestDto.sport,
            isMale = true,
            birthday = LocalDate.of(1970, 1, 1),
            weight = 88.7,
            height = 1.87
        )

        val allGames = listOf(
            SportsmenGamesResponse(
                sportsmenId = requestDto.sportsmenId,
                tournamentId = 1,
                sport = requestDto.sport,
                isOfficial = true,
                place = 2
            )
        )

        val sanctions = listOf(
            SportsmenSanction(
                sportsmenId = requestDto.sportsmenId,
                sanctionId = 1
            )
        )

        val requestPeriodGames = SportsmenGamesRequest(
            requestDto.sportsmenId,
            requestDto.sport,
            requestDto.period
        )

        val gamesLastPeriod = listOf(
            SportsmenGamesResponse(
                sportsmenId = requestDto.sportsmenId,
                tournamentId = 1,
                sport = requestDto.sport,
                isOfficial = true,
                place = 2
            )
        )

        val responseUpdating = SportsmenResponseDto(code = 0, message = "")

        tournamentStorageMockServer.stubFor(
            post(urlEqualTo("/api/v1/users/get_sportsmen_info"))
                .willReturn(
                    aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(sportsmenResponse))
                )
        )

        tournamentStorageMockServer.stubFor(
            post(urlEqualTo("/api/v1/users/get_sportsmen_games"))
                .withRequestBody(matchingJsonPath("$.period")) // Проверяем наличие "period"
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(gamesLastPeriod))
                )
        )

        tournamentStorageMockServer.stubFor(
            post(urlEqualTo("/api/v1/users/get_sportsmen_sanctions"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(sanctions))
                )
        )

        tournamentStorageMockServer.stubFor(
            post(urlEqualTo("/api/v1/users/get_sportsmen_games"))
                .withRequestBody(notMatching("$.period")) // Проверяем отсутствие "period"
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(allGames))
                )
        )

        tournamentStorageMockServer.stubFor(
            post(urlEqualTo("/api/v1/users/update_sportsmen_rate"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(responseUpdating))
                )
        )


        mvc.perform(
            MockMvcRequestBuilders.post("/api/v1/users/score")
                .content(toJson(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.message").value(""))
    }
}