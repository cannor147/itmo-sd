package com.github.cannor147.itmo.sd.lab11.model

import jakarta.persistence.*

@Entity
@Table(name = "accounts", schema = "bank", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "user_id"])])
data class Account(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(generator = "account_id", strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String = "default",

    @Column(name = "amount", nullable = false)
    var amount: Double = 0.0,

    @Column(name = "hold", nullable = false)
    var hold: Double = 0.0,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    var user: User? = null,
)
