import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
    id("org.openapi.generator") version "7.8.0"
}

detekt {
    toolVersion = "1.23.7"
    config.setFrom(file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    autoCorrect = true
    ignoreFailures = false
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("jakarta.validation:jakarta.validation-api:3.1.0")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.12")
    implementation("ch.qos.logback:logback-core:1.5.12")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.0")

    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-webflux")

    // Detekt custom rules
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")

    // OpenApi
    implementation("io.swagger.core.v3:swagger-models:2.2.23")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.23")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    // Aspect
    implementation("org.aspectj:aspectjweaver:1.9.22.1")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    testImplementation("org.wiremock:wiremock-standalone:3.9.2")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencyManagement {
    configurations.matching { it.name == "detekt" }.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.kotlin") {
                useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
            }
        }
    }
}

data class OpenApiContract(val gradleTaskName: String, val specPathSuffix: String, val generatedPackageName: String) {
    val specPath: String
        get() = "$rootDir/src/main/resources/openapi/scoring-service/v1/$specPathSuffix"
}

val openApiSpecs = listOf(
    OpenApiContract("generateStorageApi", "storage/tournament-storage.yaml", "storage"),
    OpenApiContract("generateScoringApi", "scoring/scoring-service.yaml", "scoring")
)

val outputDirPath = "${project.layout.buildDirectory.get().asFile}/generated"

openApiSpecs.forEach {
    val openApiGenerateInterface = tasks.register<GenerateTask>(it.gradleTaskName) {
        generatorName.set("kotlin-spring")
        inputSpec.set(it.specPath)
        outputDir.set(outputDirPath)
        apiPackage.set("ru.tournament.${it.generatedPackageName}.api")
        modelPackage.set("ru.tournament.${it.generatedPackageName}.dto")

        generateApiTests.set(false)
        generateModelTests.set(false)
        generateApiDocumentation.set(false)
        generateModelDocumentation.set(false)

        configOptions.putAll(
            mapOf(
                "interfaceOnly" to "true",
                "dateLibrary" to "java8",
                "serializationLibrary" to "jackson",
                "useSpringBoot3" to "true",
                "useTags" to "true",
                "requestMappingMode" to "api_interface"
            )
        )
    }

    val openApiGenerateClient = tasks.register<GenerateTask>(it.gradleTaskName + "Client") {
        generatorName.set("kotlin")
        inputSpec.set(it.specPath)
        outputDir.set(outputDirPath)
        apiPackage.set("ru.tournament.${it.generatedPackageName}.client")
        modelPackage.set("ru.tournament.${it.generatedPackageName}.dto")

        generateApiTests.set(false)
        generateModelTests.set(false)
        generateApiDocumentation.set(false)
        generateModelDocumentation.set(false)

        configOptions.putAll(
            mapOf(
                "useTags" to "false",
                "library" to "jvm-spring-webclient",
                "dateLibrary" to "java8",
                "serializationLibrary" to "jackson",
                "useSpringBoot3" to "true",
                "apiSuffix" to "ApiClient",
            )
        )
    }

    tasks.named("compileKotlin") {
        dependsOn(openApiGenerateInterface)
        dependsOn(openApiGenerateClient)
    }
}

sourceSets.main {
    java {
        srcDir("$outputDirPath/src/main/kotlin")
    }
}
