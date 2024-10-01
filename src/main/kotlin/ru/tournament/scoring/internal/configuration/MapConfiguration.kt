package ru.tournament.scoring.internal.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.tournament.scoring.logic.common.enums.Sport
import ru.tournament.scoring.logic.service.ScoringService
import java.util.*

@Configuration
class MapConfiguration(
    @Autowired private val sports: List<ScoringService>
) {

    @Bean
    fun createMapSports(): Map<Sport, ScoringService> {
        val map = EnumMap<Sport, ScoringService>(Sport::class.java)
        sports.forEach { map[it.type()] = it }
        return map
    }
}