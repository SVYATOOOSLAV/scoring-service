package ru.tournament.scoring.advicer

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException
import ru.tournament.scoring.logic.exception.Codes

@RestControllerAdvice
class WebClientExceptionHandler {

    @ExceptionHandler
    @SuppressWarnings("UnusedParameter")
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<ErrorMessage> {
        return ResponseEntity.internalServerError()
            .body(ErrorMessage(Codes.EXTERNAL_SYSTEM_ERROR))
    }
}
