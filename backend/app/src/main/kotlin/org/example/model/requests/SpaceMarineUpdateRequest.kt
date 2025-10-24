package org.example.model.requests

import org.example.JsonDeserializable
import org.example.model.AstartesCategory
import org.example.model.Weapon

@JsonDeserializable
data class SpaceMarineUpdateRequest(
    val name: String? = null,
    val coordinatesId: Long? = null,
    val chapterId: Long? = null,
    val health: Long? = null,
    val loyal: Boolean? = null,
    val category: AstartesCategory? = null,
    val weaponType: Weapon? = null,
)
