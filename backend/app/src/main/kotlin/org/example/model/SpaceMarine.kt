package org.example.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.example.model.Weapon
import java.time.LocalDateTime

@Entity
@Table(name = "space_marines")
data class SpaceMarine(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Positive
    val id: Int = 0,
    @field:NotBlank
    @Column(nullable = false)
    val name: String,
    @field:NotNull
    @Column(name = "coordinates_id", nullable = false)
    val coordinatesId: Long,
    @field:NotNull
    @Column(nullable = false, updatable = false)
    val creationDate: LocalDateTime = LocalDateTime.now(),
    @field:NotNull
    @Column(name = "chapter_id", nullable = false)
    val chapterId: Long,
    @field:NotNull
    @field:Positive
    @Column(nullable = false)
    val health: Long,
    @Column
    val loyal: Boolean? = null,
    @Enumerated(EnumType.STRING)
    @Column
    val category: AstartesCategory? = null,
    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val weaponType: Weapon,
)
