package com.github.cannor147.itmo.sd.lab11.model

import jakarta.persistence.*

@Entity
@Table(name = "stocks", schema = "bank")
data class Stock(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(generator = "stock_id", strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "code", nullable = false)
    var code: String? = null,

    @Column(name = "amount", nullable = false)
    var amount: Long = 0,

    @Column(name = "hold", nullable = false)
    var hold: Long = 0,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    var user: User? = null,
)
