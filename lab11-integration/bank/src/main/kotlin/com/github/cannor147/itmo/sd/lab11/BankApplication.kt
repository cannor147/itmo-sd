package com.github.cannor147.itmo.sd.lab11

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankApplication

fun main(args: Array<String>) {
    runApplication<BankApplication>(*args)
}
