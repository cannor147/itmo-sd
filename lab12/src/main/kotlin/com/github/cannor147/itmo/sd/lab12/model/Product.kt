package com.github.cannor147.itmo.sd.lab12.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.Date

@Entity
@Table(name = "products", schema = "shop")
data class Product(
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true)
    var id: Long = 0,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "category", nullable = false)
    var category: String? = null,

    @Column(name = "image", nullable = true)
    var image: String? = null,

    @Column(name = "number", nullable = false)
    var number: Long? = null,

    @Column(name = "price", nullable = false)
    var price: Double? = null,

    @Column(name = "currency", nullable = false)
    var currency: Currency? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    var owner: User? = null,

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    var created: Date = Date(),

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    var updated: Date = Date(),
)
