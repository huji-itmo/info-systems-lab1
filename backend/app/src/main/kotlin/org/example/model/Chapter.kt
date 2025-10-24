package org.example.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Entity
@Table(name = "chapters")
data class Chapter(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @field:NotBlank
    val name: String,
    @field:Positive
    @field:Max(1000)
    val marinesCount: Long,
)
