package com.kotlinpractice.demo.model

import jakarta.persistence.*


@Entity
data class Employee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String? = null,
    @Column(name = "email_address")
    var email: String? = null
)