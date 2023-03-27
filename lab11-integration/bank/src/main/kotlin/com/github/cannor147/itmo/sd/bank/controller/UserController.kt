package com.github.cannor147.itmo.sd.bank.controller

import com.github.cannor147.itmo.sd.bank.service.AccountService
import com.github.cannor147.itmo.sd.bank.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@ControllerAdvice(basePackageClasses = [UserController::class])
@RequestMapping("/banking/{login}")
class UserController(
    private val userService: UserService,
    private val accountService: AccountService,
) {
    @GetMapping("/register")
    fun create(
        @PathVariable("login") login: String,
    ) = ModelAndView("redirect:/banking/$login").addObject("user", userService.create(login))

    @GetMapping("")
    fun view(
        @PathVariable("login") login: String,
    ) = ModelAndView(BANKING).addObject("user", userService.get(login))

    @PostMapping("/deposit")
    fun deposit(
        @PathVariable("login") login: String,
        @RequestParam("account") accountName: String,
        @RequestParam("amount") amount: Double,
    ) = try {
        accountService.deposit(amount, login, accountName)
        ModelAndView("redirect:/banking/$login")
    } catch (e: IllegalArgumentException) {
        view(login).addObject("error", e.message)
    } catch (e: IllegalStateException) {
        view(login).addObject("error", e.message)
    }

    @PostMapping("/withdraw")
    fun withdraw(
        @PathVariable("login") login: String,
        @RequestParam("account") accountName: String,
        @RequestParam("amount") amount: Double,
    ) = try {
        accountService.withdraw(amount, login, accountName)
        ModelAndView("redirect:/banking/$login")
    } catch (e: IllegalArgumentException) {
        view(login).addObject("error", e.message)
    } catch (e: IllegalStateException) {
        view(login).addObject("error", e.message)
    }

    companion object {
        private const val BANKING = "Banking"
    }
}