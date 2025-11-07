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
        @JsonbProperty("name")
        val name: String,
        @field:NotNull
        @field:Positive
        @JsonbProperty("coordinatesId")
        val coordinatesId: Long,
        @field:NotNull
        @field:Positive
        @JsonbProperty("chapterId")
        val chapterId: Long,
        @field:NotNull
        @field:Positive
        @JsonbProperty("health")
        val health: Long,
        @JsonbProperty("loyal")
        val loyal: Boolean?,
        @field:JsonbProperty("category")
        val category: String?,
        @field:NotNull
        @JsonbProperty("weaponType")
        val weaponType: String,
    )
