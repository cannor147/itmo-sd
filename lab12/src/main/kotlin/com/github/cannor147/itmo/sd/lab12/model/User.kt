package com.github.cannor147.itmo.sd.lab12.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "users", schema = "shop")
data class User(
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    var id: Long = 0,

    @Column(name = "login", nullable = false, unique = true)
    var login: String? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "currency", nullable = false)
    var currency: Currency? = null,

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    var created: Date = Date(),

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    var updated: Date = Date(),
)
