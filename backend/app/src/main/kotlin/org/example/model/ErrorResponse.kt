package org.example.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.example.JsonDeserializable
import java.time.LocalDateTime

@JsonDeserializable
data class ErrorResponse(
    val error: String,
    val message: String,
    val path: String,
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val fieldErrors: List<FieldError>? = null,
)

@JsonDeserializable
data class FieldError(
    val field: String,
    val message: String,
    val rejectedValue: Any? = null,
)
