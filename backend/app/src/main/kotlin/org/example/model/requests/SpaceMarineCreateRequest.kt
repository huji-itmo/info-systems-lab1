package org.example.model.requests

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.example.JsonDeserializable
import org.example.model.AstartesCategory
import org.example.model.Weapon

@JsonDeserializable
data class SpaceMarineCreateRequest(
    @field:NotBlank
    val name: String,
    @field:NotNull
    @field:Positive
    val coordinatesId: Long,
    @field:NotNull
    @field:Positive
    val chapterId: Long,
    @field:NotNull
    @field:Positive
    val health: Long,
    val loyal: Boolean? = null,
    val category: AstartesCategory? = null,
    @field:NotNull
    val weaponType: Weapon,
)
