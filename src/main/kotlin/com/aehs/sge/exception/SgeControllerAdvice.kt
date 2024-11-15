package com.aehs.sge.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.function.Consumer

@RestControllerAdvice
class SgeControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message)
        problemDetail.title = "Not found"
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message)
        problemDetail.title = "Bad Request"
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: ForbiddenException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.message)
        problemDetail.title = "Forbidden"
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail)
    }

    @ExceptionHandler(DuplicatedException::class)
    fun handleDuplicatedException(ex: ForbiddenException): ResponseEntity<ProblemDetail> {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message)
        problemDetail.title = "Duplicated entity"
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleInvalidArgumentsException(ex: MethodArgumentNotValidException): ResponseEntity<ProblemDetail> {
        val errors: MutableList<ErrorField> = ArrayList()
        ex.bindingResult.allErrors.forEach(Consumer { error: ObjectError ->
            val field = (error as FieldError).field
            val message = error.getDefaultMessage()
            errors.add(ErrorField(field, message!!))
        })

        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.message)
        problemDetail.title = "Invalid arguments"
        problemDetail.properties = mapOf("errors" to errors)
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail)
    }
}

data class ErrorField(val field: String, val message: String)
