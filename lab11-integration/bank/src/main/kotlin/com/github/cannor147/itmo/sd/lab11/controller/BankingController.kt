package com.github.cannor147.itmo.sd.lab11.controller

import com.github.cannor147.itmo.sd.lab11.service.AccountService
import com.github.cannor147.itmo.sd.lab11.service.ExchangeService
import com.github.cannor147.itmo.sd.lab11.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@ControllerAdvice(basePackageClasses = [BankingController::class])
@RequestMapping("/banking/{login}")
class BankingController(
    private val userService: UserService,
    private val accountService: AccountService,
    private val exchangeService: ExchangeService,
) {
    @GetMapping("/register")
    fun create(
        @PathVariable("login") login: String,
    ) = ModelAndView("redirect:/banking/$login").addObject("user", userService.create(login))

    @GetMapping("")
    fun view(
        @PathVariable("login") login: String,
    ) = ModelAndView(BANKING)
        .addObject("user", userService.get(login))
        .addObject("companies", exchangeService.getCompanies(login))

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

    @PostMapping("/exchange/buy")
    fun buy(
        @PathVariable("login") login: String,
        @RequestParam("id") id: String,
        @RequestParam("account") accountName: String,
    ) = try {
        exchangeService.buy(login, id, accountName)
        ModelAndView("redirect:/banking/$login")
    } catch (e: IllegalArgumentException) {
        view(login).addObject("error", e.message)
    } catch (e: IllegalStateException) {
        view(login).addObject("error", e.message)
    }

    @PostMapping("/exchange/sell")
    fun sell(
        @PathVariable("login") login: String,
        @RequestParam("code") code: String,
        @RequestParam("account") accountName: String,
        @RequestParam("amount") amount: Long,
    ) = try {
        exchangeService.sell(login, code, amount, accountName)
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