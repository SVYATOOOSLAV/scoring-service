package ru.tournament.scoring

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import com.github.tomakehurst.wiremock.junit5.WireMockTest
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WireMockTest
@ActiveProfiles("local")
class AbstractMockMvc {

    @Autowired
    lateinit var mvc: MockMvc

    companion object {
        private var mapper: ObjectMapper = ObjectMapper().findAndRegisterModules()

        fun toJson(dto: Any): String {
            return mapper.writeValueAsString(dto)
        }

        @JvmStatic
        @RegisterExtension
        val tournamentStorageMockServer: WireMockExtension =
            WireMockExtension.newInstance()
                .options(wireMockConfig().dynamicPort())
                .build()

        @JvmStatic
        @DynamicPropertySource
        fun configuredProperties(registry: DynamicPropertyRegistry) {
            registry.add("client.tournament-storage.url", tournamentStorageMockServer::baseUrl)
        }

    }
}