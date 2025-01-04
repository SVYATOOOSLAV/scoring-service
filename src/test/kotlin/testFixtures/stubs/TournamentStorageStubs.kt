package testFixtures.stubs

import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath
import com.github.tomakehurst.wiremock.client.WireMock.notMatching
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import org.springframework.http.MediaType
import ru.tournament.scoring.AbstractMockMvc.Companion.toJson
import ru.tournament.scoring.AbstractMockMvc.Companion.tournamentStorageMockServer
import ru.tournament.scoring.dto.SportsmenSanction
import ru.tournament.storage.dto.SportsmenGamesResponse
import ru.tournament.storage.dto.SportsmenInfoResponse
import ru.tournament.storage.dto.SportsmenRateResponse

fun getSportsmenInfoStub(statusCode: Int, response: SportsmenInfoResponse) {
    tournamentStorageMockServer.stubFor(
        post(urlEqualTo("/api/v1/users/get_sportsmen_info"))
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(toJson(response))
            )
    )
}

fun getSportsmenGamesStub(statusCode: Int, response: List<SportsmenGamesResponse>) {
    tournamentStorageMockServer.stubFor(
        post(urlEqualTo("/api/v1/users/get_sportsmen_games"))
            .withRequestBody(notMatching("$.period"))
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(toJson(response))
            )
    )
}

fun getSportsmenGamesLastPeriodStub(statusCode: Int, response: List<SportsmenGamesResponse>) {
    tournamentStorageMockServer.stubFor(
        post(urlEqualTo("/api/v1/users/get_sportsmen_games"))
            .withRequestBody(matchingJsonPath("$.period"))
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(toJson(response))
            )
    )
}

fun getSportsmenSanctionsStub(statusCode: Int, response: List<SportsmenSanction>) {
    tournamentStorageMockServer.stubFor(
        post(urlEqualTo("/api/v1/users/get_sportsmen_sanctions"))
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(toJson(response))
            )
    )
}

fun updateSportsmenRateStub(statusCode: Int, response: SportsmenRateResponse) {
    tournamentStorageMockServer.stubFor(
        post(urlEqualTo("/api/v1/users/update_sportsmen_rate"))
            .willReturn(
                aResponse()
                    .withStatus(statusCode)
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withBody(toJson(response))
            )
    )
}
