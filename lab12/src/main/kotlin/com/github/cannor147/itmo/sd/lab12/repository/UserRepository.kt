package com.github.cannor147.itmo.sd.lab12.repository

import com.github.cannor147.itmo.sd.lab12.model.User
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserRepository : CoroutineCrudRepository<User, Long> {
    fun findByLogin(login: String): Mono<User>
}