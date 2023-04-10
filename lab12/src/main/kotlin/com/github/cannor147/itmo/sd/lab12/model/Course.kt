package com.github.cannor147.itmo.sd.lab12.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "courses", schema = "shop")
data class Course(
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    var id: Long = 0,

    @Column(name = "from_currency", nullable = false)
    var from: Currency? = null,

    @Column(name = "to_currency", nullable = false)
    var to: Currency? = null,

    @Column(name = "price", nullable = false)
    var price: Double? = null,

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    var created: Date = Date(),

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    var updated: Date = Date(),
)
