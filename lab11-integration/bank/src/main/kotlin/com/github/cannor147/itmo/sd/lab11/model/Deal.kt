package com.github.cannor147.itmo.sd.lab11.model

import jakarta.persistence.*

@Entity
@Table(name = "deals", schema = "bank")
data class Deal(
    @Id
    @Column(name = "id", nullable = false, unique = true)
    var id: String? = null,

    @Column(name = "handled", nullable = false)
    var handled: Boolean = false,

    @Column(name = "account", nullable = false)
    var account: String? = null,

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    var user: User? = null,
)
