package ru.tournament.scoring.integration_test

import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity.post
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import ru.tournament.model.*
import ru.tournament.scoring.AbstractMockMvc
import java.time.LocalDate
import java.util.*

@WireMockTest
@ActiveProfiles("local")
class SportsmenControllerTest: AbstractMockMvc() {

    @Test
    fun positiveScoreSportsmenTest(){
        val requestDto = SportsmenRequestDto(
            sportsmenId = 1,
            sport = "box",
            period = 12
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
            SportsmenGame(
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

        val gamesLastPeriod = listOf(
            SportsmenGame(
                sportsmenId = requestDto.sportsmenId,
                tournamentId = 1,
                sport = requestDto.sport,
                isOfficial = true,
                place = 2
            )
        )

        val responseUpdating = SportsmenResponseDto(code = 0, message = "")

        val expectedRate = 0.45

        tournamentStorageMockServer.stubFor(
            get("/api/v1/users/${requestDto.sportsmenId}/${requestDto.sport}")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(sportsmenResponse))
                )
        )

        tournamentStorageMockServer.stubFor(
            get("/api/v1/users/${requestDto.sportsmenId}/${requestDto.sport}/games")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(allGames))
                )
        )

        tournamentStorageMockServer.stubFor(
            get("/api/v1/users/${requestDto.sportsmenId}/${requestDto.sport}/sanctions/${requestDto.period}")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(sanctions))
                )
        )

        tournamentStorageMockServer.stubFor(
            get("/api/v1/users/${requestDto.sportsmenId}/${requestDto.sport}/games/${requestDto.period}")
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(toJson(gamesLastPeriod))
                )
        )

        tournamentStorageMockServer.stubFor(
            post(urlEqualTo("/api/v1/users/${requestDto.sportsmenId}/${requestDto.sport}/${expectedRate}"))
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