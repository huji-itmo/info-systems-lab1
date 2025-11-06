package org.example.exception.mapper

import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.example.model.ErrorResponse

@Provider
class ValidationExceptionMapper : ExceptionMapper<ConstraintViolationException> {
    @Context
    lateinit var uriInfo: UriInfo

    override fun toResponse(exception: ConstraintViolationException): Response {
        val violations = exception.constraintViolations
        val message = violations.joinToString("; ") { it.message }
        val error =
            ErrorResponse(
                error = "VALIDATION_ERROR",
                message = message,
                path = uriInfo.path,
            )
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(error)
            .build()
    }
}
