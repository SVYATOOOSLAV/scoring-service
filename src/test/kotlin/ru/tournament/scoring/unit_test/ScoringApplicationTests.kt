package ru.tournament.scoring.unit_test

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class ScoringApplicationTests {

    @Test
    fun contextLoads() {
        println("Hello world")
    }
}
