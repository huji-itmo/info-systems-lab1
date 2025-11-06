package org.example.exceptions

import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.example.model.ErrorResponse

@Provider
class IllegalArgumentExceptionMapper : ExceptionMapper<IllegalArgumentException> {
    @Context
    lateinit var uriInfo: UriInfo

    override fun toResponse(exception: IllegalArgumentException): Response {
        val error =
            ErrorResponse(
                error = "INVALID_INPUT",
                message = exception.message ?: "Invalid input provided",
                path = uriInfo.path,
                fieldErrors =
                    listOf(
                        FieldError(
                            field = extractFieldName(exception.message),
                            message = exception.message ?: "Invalid value",
                        ),
                    ).takeIf { it.isNotEmpty() },
            )
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(error)
            .build()
    }

    private fun extractFieldName(message: String?): String =
        when {
            message?.contains("page") == true -> "page"
            message?.contains("size") == true -> "size"
            else -> "input"
        }
}
