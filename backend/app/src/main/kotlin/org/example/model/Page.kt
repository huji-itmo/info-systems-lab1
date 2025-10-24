package org.example.model

import org.example.JsonDeserializable

@JsonDeserializable
data class Page<T>(
    val content: List<T>,
    val page: Int,
    val size: Int,
    val totalElements: Long,
    val totalPages: Int,
)
