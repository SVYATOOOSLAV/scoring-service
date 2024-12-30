package ru.tournament.scoring.logic.client

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import ru.tournament.client.TournamentStorageApiClient
import ru.tournament.model.*
import ru.tournament.scoring.logic.common.model.Result

private val logger = KotlinLogging.logger {}

@Service
class TournamentStorageService(
    private val client: TournamentStorageApiClient
) {

    fun getSportsmenInfo(infoRequest: SportsmenInfoRequest): SportsmenInfoResponse {
        return client.getSportsmenInfo(infoRequest)
            .doOnError { ex ->
                logger.error { "Failed to fetch sportsmen info with sportsmen id: [${infoRequest.id}] and sport: [${infoRequest.sport}]" }
                throw ex
            }
            .block()!!
    }

    fun getSportsmenGames(gamesRequest: SportsmenGamesRequest): List<SportsmenGamesResponse> {
        return client.getSportsmenGames(gamesRequest)
            .doOnError { ex ->
                logger.warn { "Failed to fetch sportsmen games with sportsmen id: [${gamesRequest.id}] and sport: [${gamesRequest.sport}]" }
            }
            .onErrorResume(WebClientResponseException::class.java) { ex ->
                when (ex.statusCode.value()) {
                    in 400..499 -> {
                        logger.warn { "Client error: ${ex.message}" }
                        Mono.just(emptyList())
                    }
                    in 500..599 -> {
                        logger.warn { "Server error: ${ex.message}" }
                        Mono.just(emptyList())
                    }
                    else -> Mono.just(emptyList())
                }
            }
            .block().orEmpty()
    }


    fun getSportsmenSanction(sanctionsRequest: SportsmenSanctionsRequest): List<SportsmenSanctionsResponse> {
        return client.getSportsmenSanctions(sanctionsRequest)
            .doOnError { ex ->
                logger.warn { "Failed to fetch sportsmen sanctions with sportsmen id: [${sanctionsRequest.id}] and sport: [${sanctionsRequest.sport}]" }
            }
            .onErrorResume(WebClientResponseException::class.java) { ex ->
                when (ex.statusCode.value()) {
                    in 400..499 -> {
                        logger.warn { "Client error: ${ex.message}" }
                        Mono.just(emptyList())
                    }
                    in 500..599 -> {
                        logger.warn { "Server error: ${ex.message}" }
                        Mono.just(emptyList())
                    }
                    else -> Mono.just(emptyList())
                }
            }
            .block().orEmpty()
    }

    fun updateRateSportsmen(sportsmenRateRequest: SportsmenRateRequest): Result {
        val response = client.updateSportsmenRate(sportsmenRateRequest)
            .doOnError { ex ->
                logger.error { "Failed to update sportsmen rate for sportsmen id: [${sportsmenRateRequest.id}] and sport: [${sportsmenRateRequest.sport}]" }
                throw ex
            }
            .block()!!

        return Result(response.code, response.message)
    }
}
