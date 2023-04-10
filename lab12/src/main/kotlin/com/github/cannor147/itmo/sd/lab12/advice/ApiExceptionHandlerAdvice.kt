package com.github.cannor147.itmo.sd.lab12.advice

import com.github.cannor147.itmo.sd.lab10.advice.AbstractExceptionHandlerAdvice
import com.github.cannor147.itmo.sd.lab10.dto.StatusDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class ApiExceptionHandlerAdvice : AbstractExceptionHandlerAdvice<ResponseEntity<StatusDto>>() {
    override fun error(status: HttpStatus, e: Exception) = ResponseEntity.status(status)
        .body(StatusDto(status = "error", message = e.message ?: UNKNOWN_ERROR))
}
