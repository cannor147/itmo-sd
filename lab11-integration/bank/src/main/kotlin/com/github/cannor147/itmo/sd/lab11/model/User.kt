package com.github.cannor147.itmo.sd.lab11.model

import jakarta.persistence.*

@Entity
@Table(name = "users", schema = "bank")
data class User(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(generator = "user_id", strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "login", nullable = false, unique = true)
    var login: String? = null,
)
