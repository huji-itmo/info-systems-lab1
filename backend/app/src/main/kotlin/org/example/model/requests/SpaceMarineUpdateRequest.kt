package org.example.model.requests

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty
import org.example.model.AstartesCategory
import org.example.model.Weapon

data class SpaceMarineUpdateRequest
    @JsonbCreator
    constructor(
        @JsonbProperty("name") val name: String?,
        @JsonbProperty("coordinatesId") val coordinatesId: Long?,
        @JsonbProperty("chapterId") val chapterId: Long?,
        @JsonbProperty("health") val health: Long?,
        @JsonbProperty("loyal") val loyal: Boolean?,
        @JsonbProperty("category") val category: String?,
        @JsonbProperty("weaponType") val weaponType: String,
    )
