package ru.tournament.scoring.internal.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tournament.client.TournamentStorageApiClient
import ru.tournament.scoring.internal.configuration.properties.TournamentStorageProperties

@Configuration
@EnableConfigurationProperties(TournamentStorageProperties::class)
class TournamentStorageConfiguration(
    val storageProperties: TournamentStorageProperties
) {
    @Bean
    fun getTournamentStorageWebClient(): TournamentStorageApiClient {
        return TournamentStorageApiClient(
            baseUrl = storageProperties.url
        )
    }
}
