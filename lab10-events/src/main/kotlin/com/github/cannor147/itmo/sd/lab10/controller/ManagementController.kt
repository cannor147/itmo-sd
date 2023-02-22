package com.github.cannor147.itmo.sd.lab10.controller

import com.github.cannor147.itmo.sd.lab10.service.SubscriptionService
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/manage")
class ManagementController(
    private val subscriptionService: SubscriptionService,
) {
    @GetMapping("/{phoneNumber:([+]7|8)[a-z-]+}")
    fun get(
        @PathVariable("phoneNumber") @Pattern(regexp = ) phoneNumber: String
    ): ModelAndView {
        val modelAndView = ModelAndView("Subscription")
        modelAndView.addObject("subscription", subscriptionService.get(phoneNumber))
    }
}