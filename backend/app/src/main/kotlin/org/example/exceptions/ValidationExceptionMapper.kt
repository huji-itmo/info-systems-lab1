package org.example.exception.mapper

import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.UriInfo
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider
import org.example.model.ErrorResponse
import org.example.model.FieldError

@Provider
class ValidationExceptionMapper : ExceptionMapper<ConstraintViolationException> {
    @Context
    lateinit var uriInfo: UriInfo

    override fun toResponse(exception: ConstraintViolationException): Response {
        val fieldErrors = exception.constraintViolations.map { toFieldError(it) }
        val message = fieldErrors.joinToString("; ") { "${it.field}: ${it.message}" }

        val error =
            ErrorResponse(
                error = "VALIDATION_ERROR",
                message = message,
                path = uriInfo.path,
                fieldErrors = fieldErrors,
            )

        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(error)
            .build()
    }

    private fun toFieldError(violation: ConstraintViolation<*>): FieldError =
        FieldError(
            field = violation.propertyPath.toString(),
            message = violation.message,
            rejectedValue = violation.invalidValue,
        )
}
