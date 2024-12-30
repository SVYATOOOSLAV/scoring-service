package ru.tournament.scoring.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import ru.tournament.client.TournamentStorageApiClient
import ru.tournament.scoring.configuration.properties.TournamentStorageProperties

@Configuration
@EnableConfigurationProperties(TournamentStorageProperties::class)
class TournamentStorageConfiguration(
    val storageProperties: TournamentStorageProperties
) {
    @Bean
    fun getTournamentStorageWebClient(): TournamentStorageApiClient {
        val webClient = WebClient.builder()
            .baseUrl(storageProperties.url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build()

        return TournamentStorageApiClient(webClient)
    }
}
