package com.github.cannor147.itmo.sd.lab11.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "deals", schema = "exchange")
data class Deal(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: String = UUID.randomUUID().toString(),

    @Column(name = "auto_renewable", nullable = false)
    var autoRenewable: Boolean = false,

    @Column(name = "seller", nullable = true)
    var seller: String? = null,

    @Column(name = "buyer", nullable = true)
    var buyer: String? = null,

    @Column(name = "amount", nullable = false)
    var amount: Long? = null,

    @Column(name = "final_price", nullable = true)
    var finalPrice: Double? = null,

    @JoinColumn(name = "stock_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    var stock: Stock? = null,

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    var created: LocalDateTime = LocalDateTime.now(),

    @Column(name = "paid_date", nullable = true)
    var paid: LocalDateTime? = null,

    @Column(name = "deleted", nullable = false)
    var deleted: Boolean = false,
)
