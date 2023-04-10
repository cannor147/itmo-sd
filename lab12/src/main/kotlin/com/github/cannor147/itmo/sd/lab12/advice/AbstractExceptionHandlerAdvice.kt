package com.github.cannor147.itmo.sd.lab10.advice

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
abstract class AbstractExceptionHandlerAdvice<Error> {
    companion object {
        const val UNKNOWN_ERROR = "Unknown error"
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handle(e: IllegalArgumentException) = error(HttpStatus.BAD_REQUEST, e)

    @ExceptionHandler(IllegalStateException::class)
    fun handle(e: IllegalStateException) = error(HttpStatus.BAD_REQUEST, e)

    @ExceptionHandler(UnsupportedOperationException::class)
    fun handle(e: UnsupportedOperationException) = error(HttpStatus.BAD_REQUEST, e)

    @ExceptionHandler(IllegalAccessException::class)
    fun handle(e: IllegalAccessException) = error(HttpStatus.UNAUTHORIZED, e)

    @ExceptionHandler(IllegalCallerException::class)
    fun handle(e: IllegalCallerException) = error(HttpStatus.FORBIDDEN, e)

    @ExceptionHandler(IndexOutOfBoundsException::class)
    fun handle(e: IndexOutOfBoundsException) = error(HttpStatus.NOT_FOUND, e)

    @ExceptionHandler(NoSuchElementException::class)
    fun handle(e: NoSuchElementException) = error(HttpStatus.NOT_FOUND, e)

    @ExceptionHandler(NoSuchFileException::class)
    fun handle(e: NoSuchFileException) = error(HttpStatus.NOT_FOUND, e)

    abstract fun error(status: HttpStatus, e: Exception): Error
}
