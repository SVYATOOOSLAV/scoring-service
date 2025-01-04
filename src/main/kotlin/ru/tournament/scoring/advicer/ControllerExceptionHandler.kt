package ru.tournament.scoring.advicer

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.tournament.scoring.logic.exception.BusinessLogicException
import ru.tournament.scoring.logic.exception.ValidationErrorException

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler
    fun handleValidationErrors(ex: ValidationErrorException): ResponseEntity<ErrorMessage> {
        return ResponseEntity
            .badRequest()
            .body(ErrorMessage(ex.code, ex.message))
    }

    @ExceptionHandler
    fun handleBusinessLogicErrors(ex: BusinessLogicException): ResponseEntity<ErrorMessage> {
        return ResponseEntity
            .ok()
            .body(ErrorMessage(ex.code, ex.message))
    }
}
