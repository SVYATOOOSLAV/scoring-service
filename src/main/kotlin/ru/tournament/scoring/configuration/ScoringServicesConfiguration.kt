package ru.tournament.scoring.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.service.ScoringService
import java.util.*

@Configuration
class ScoringServicesConfiguration {

    @Bean
    fun scoringServices(sports: List<ScoringService>): Map<Sport, ScoringService> {
        val map = EnumMap<Sport, ScoringService>(Sport::class.java)
        sports.forEach { map[it.type()] = it }
        return map
    }
}
