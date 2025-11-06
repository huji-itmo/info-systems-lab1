package org.example.model.requests

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty
import org.example.model.AstartesCategory
import org.example.model.Weapon

data class SpaceMarineUpdateRequest
    @JsonbCreator
    constructor(
        @field:JsonbProperty("name") val name: String? = null,
        @field:JsonbProperty("coordinatesId") val coordinatesId: Long? = null,
        @field:JsonbProperty("chapterId") val chapterId: Long? = null,
        @field:JsonbProperty("health") val health: Long? = null,
        @field:JsonbProperty("loyal") val loyal: Boolean? = null,
        @field:JsonbProperty("category") val category: String? = null,
        @field:JsonbProperty("weaponType") val weaponType: String? = null,
    )
