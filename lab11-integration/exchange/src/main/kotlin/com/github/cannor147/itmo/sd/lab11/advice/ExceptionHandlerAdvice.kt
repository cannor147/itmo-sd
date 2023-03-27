package com.github.cannor147.itmo.sd.lab11.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handle(e: IllegalArgumentException) = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)

    @ExceptionHandler(IllegalStateException::class)
    fun handle(e: IllegalStateException) = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)

    @ExceptionHandler(IllegalAccessException::class)
    fun handle(e: IllegalAccessException) = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)

    @ExceptionHandler(IllegalCallerException::class)
    fun handle(e: IllegalCallerException) = ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.message)

    @ExceptionHandler(NoSuchElementException::class)
    fun handle(e: NoSuchElementException) = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)

    @ExceptionHandler(NoSuchFileException::class)
    fun handle(e: NoSuchFileException) = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
}