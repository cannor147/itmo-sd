package com.github.cannor147.itmo.sd.lab11.controller

import com.github.cannor147.itmo.sd.lab11.dto.DealDto
import com.github.cannor147.itmo.sd.lab11.dto.DealMode
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import com.github.cannor147.itmo.sd.lab11.service.DealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/deals")
class DealController(
    private val dealService: DealService,
) {
    @GetMapping("/{id}")
    fun get(
        @PathVariable("id") id: String,
        @RequestParam("login") login: String,
    ) : ResponseEntity<DealDto> = ResponseEntity.ok(dealService.get(id, login))

    @GetMapping("/my")
    fun getMy(
        @RequestParam("login") login: String,
        @RequestParam("deleted", required = false) includeDeleted: Boolean = false,
    ) : ResponseEntity<List<MyDealDto>> = ResponseEntity.ok(dealService.getByLogin(login, includeDeleted))

    @PostMapping("/{id}/buy")
    fun buy(
        @PathVariable("id") id: String,
        @RequestParam("login") login: String,
        @RequestParam("amount", required = false) amount: Long?,
    ) : ResponseEntity<MyDealDto> = ResponseEntity.ok(dealService.buyOrSell(id, login, amount, DealMode.BUY))

    @PostMapping("/{id}/sell")
    fun sell(
        @PathVariable("id") id: String,
        @RequestParam("login") login: String,
        @RequestParam("amount", required = false) amount: Long?,
    ) : ResponseEntity<MyDealDto> = ResponseEntity.ok(dealService.buyOrSell(id, login, amount, DealMode.SELL))

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable("id") id: String,
        @RequestParam("login") login: String,
    ) : ResponseEntity<Unit> {
        dealService.delete(id, login)
        return ResponseEntity.noContent().build()
    }
}