package org.example.model

import java.time.LocalDateTime

data class ErrorResponse(
    val error: String,
    val message: String,
    val path: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val fieldErrors: List<FieldError>? = null,
)

data class FieldError(
    val field: String,
    val message: String,
    val rejectedValue: Any? = null,
)
