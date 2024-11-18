package ru.tournament.scoring

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
class ScoringApplicationTests {

    @Test
    fun contextLoads() {
        println("Hello world")
    }
}
