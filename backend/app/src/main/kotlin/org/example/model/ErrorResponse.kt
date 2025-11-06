package org.example.model

data class ErrorResponse(
    val error: String,
    val message: String,
    val timestamp: String =
        java.time.Instant
            .now()
            .toString(),
    val path: String? = null,
)
