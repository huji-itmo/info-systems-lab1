package org.example.exceptions

import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.core.Response

class NotFoundException(
    message: String,
) : WebApplicationException(
        Response.status(Response.Status.NOT_FOUND).entity(message).build(),
    )
