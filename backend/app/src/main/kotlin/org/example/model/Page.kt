package org.example.model

import jakarta.json.bind.annotation.JsonbCreator
import jakarta.json.bind.annotation.JsonbProperty

data class Page<T>
    @JsonbCreator
    constructor(
        @JsonbProperty("content") val content: List<T>,
        @JsonbProperty("page") val page: Int,
        @JsonbProperty("size") val size: Int,
        @JsonbProperty("totalElements") val totalElements: Long,
        @JsonbProperty("totalPages") val totalPages: Int,
    )
