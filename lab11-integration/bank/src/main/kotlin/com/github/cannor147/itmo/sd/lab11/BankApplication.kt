package com.github.cannor147.itmo.sd.lab11

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class BankApplication

fun main(args: Array<String>) {
    runApplication<BankApplication>(*args)
}
