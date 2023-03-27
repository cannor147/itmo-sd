package com.github.cannor147.itmo.sd.bank.advice

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice
class ExceptionHandlerAdvice {
    @ExceptionHandler(IllegalAccessException::class)
    fun handle(e: IllegalAccessException) = ModelAndView("Error").addObject("error", e.message)

    @ExceptionHandler(IllegalCallerException::class)
    fun handle(e: IllegalCallerException) = ModelAndView("Error").addObject("error", e.message)

    @ExceptionHandler(NoSuchElementException::class)
    fun handle(e: NoSuchElementException) = ModelAndView("Error").addObject("error", e.message)

    @ExceptionHandler(NoSuchFileException::class)
    fun handle(e: NoSuchFileException) = ModelAndView("Error").addObject("error", e.message)
}