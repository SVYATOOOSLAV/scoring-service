package ru.tournament.scoring.advicer

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException


@RestControllerAdvice
class WebClientExceptionHandler {

    @ExceptionHandler
    fun handleWebClientResponseException(ex: WebClientResponseException): ResponseEntity<ErrorMessage> {
        return ResponseEntity.internalServerError()
            .body(ErrorMessage(-99, "Ошибки внешеней системы"))
    }
}