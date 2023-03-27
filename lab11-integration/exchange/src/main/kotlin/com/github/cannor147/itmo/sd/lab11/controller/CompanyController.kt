package com.github.cannor147.itmo.sd.lab11.controller

import com.github.cannor147.itmo.sd.lab11.dto.CompanyDto
import com.github.cannor147.itmo.sd.lab11.dto.MyDealDto
import com.github.cannor147.itmo.sd.lab11.dto.OpenDealDto
import com.github.cannor147.itmo.sd.lab11.service.CompanyService
import com.github.cannor147.itmo.sd.lab11.service.DealService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/v1/company")
class CompanyController(
    private val companyService: CompanyService,
    private val dealService: DealService,
) {
    @GetMapping("/{code}")
    fun get(
        @PathVariable("code") code: String,
    ) : ResponseEntity<CompanyDto> = ResponseEntity.ok(companyService.get(code))

    @PostMapping("/search")
    fun search(
        @RequestParam("name") name: String,
    ) : ResponseEntity<List<CompanyDto>> = ResponseEntity.ok(companyService.search(name))

    @PostMapping("/list")
    fun getAll() : ResponseEntity<List<CompanyDto>> = ResponseEntity.ok(companyService.getAll())

    @GetMapping("/{code}/deals")
    fun getDeals(
        @PathVariable("code") code: String,
    ) : ResponseEntity<List<OpenDealDto>> = ResponseEntity.ok(dealService.getByCompany(code))

    @GetMapping("/{code}/buy")
    fun buy(
        @PathVariable("code") code: String,
        @RequestParam("login") login: String,
        @RequestParam("amount") amount: Long,
        @RequestParam("auto-renewable", required = false) autoRenewable: Boolean = false,
    ) : ResponseEntity<MyDealDto> = ResponseEntity.ok(dealService.createToBuy(login, amount, code, autoRenewable))

    @GetMapping("/{code}/sell")
    fun sell(
        @PathVariable("code") code: String,
        @RequestParam("login") login: String,
        @RequestParam("amount") amount: Long,
        @RequestParam("auto-renewable", required = false) autoRenewable: Boolean = false,
    ) : ResponseEntity<MyDealDto> = ResponseEntity.ok(dealService.createToSell(login, amount, code, autoRenewable))

    @PostMapping("/{code}")
    fun create(
        @PathVariable("code") code: String,
        @RequestParam("name") name: String,
        @RequestParam("price") price: Double,
    ) : ResponseEntity<CompanyDto> {
        val company = companyService.create(code, name, price)
        return ResponseEntity.created(URI.create("/api/v1/company/${company.code}")).body(company)
    }

    @PatchMapping("/{code}")
    fun updatePrice(
        @PathVariable("code") code: String,
        @RequestParam("price") price: Double,
    ) : ResponseEntity<CompanyDto> = ResponseEntity.ok(companyService.updatePrice(code, price))

    @DeleteMapping("/{code}")
    fun delete(
        @PathVariable("code") code: String,
    ) : ResponseEntity<Unit> {
        companyService.delete(code)
        return ResponseEntity.noContent().build()
    }
}