package org.example.model.requests

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.example.model.AstartesCategory
import org.example.model.Weapon

data class SpaceMarineCreateRequest
    @JsonbCreator
    constructor(
        @field:NotBlank
        @field:JsonbProperty("name")
        val name: String,
        @field:NotNull
        @field:Positive
        @field:JsonbProperty("coordinatesId")
        val coordinatesId: Long,
        @field:NotNull
        @field:Positive
        @field:JsonbProperty("chapterId")
        val chapterId: Long,
        @field:NotNull
        @field:Positive
        @field:JsonbProperty("health")
        val health: Long,
        @field:JsonbProperty("loyal")
        val loyal: Boolean?,
        @field:JsonbProperty("category")
        val category: String?,
        @field:NotNull
        @field:JsonbProperty("weaponType")
        val weaponType: String,
    )
