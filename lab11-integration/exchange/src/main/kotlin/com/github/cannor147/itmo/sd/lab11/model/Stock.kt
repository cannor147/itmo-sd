package com.github.cannor147.itmo.sd.lab11.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "stocks", schema = "exchange")
data class Stock(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(generator = "stock_id", strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "code", length = 8, nullable = false, unique = true)
    var code: String? = null,

    @Column(name = "company", nullable = false, unique = false)
    var company: String? = null,

    @Column(name = "price", nullable = true)
    var price: Double? = null,

    @OneToMany(fetch = FetchType.LAZY)
    var deals: List<Deal> = emptyList(),

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    var created: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false,
)
